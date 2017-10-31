package com.group10.controllers.commons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group10.dao.customerDashboard.CustomerDashboardDaoImpl;
import com.group10.dbmodels.CheckingAccount;
import com.group10.dbmodels.CompletedTransaction;
import com.group10.dbmodels.CreditAccount;
import com.group10.dbmodels.PendingTransaction;
import com.group10.dbmodels.SavingsAccount;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class Download {

	@RequestMapping("/customer/downloadSavings")
    public void savingStatement(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            // Get the text that will be added to the PDF
            int userId=(Integer)request.getSession().getAttribute("userID");
            ClassPathXmlApplicationContext  ctx = new ClassPathXmlApplicationContext("DaoDetails.xml"); 
    	    CustomerDashboardDaoImpl sdao = ctx.getBean("customerDashboardDaoImpl" , CustomerDashboardDaoImpl.class);
			
    	    SavingsAccount savings = sdao.savingsAccountDetails(1);
    	    
    	    List<PendingTransaction> pendingSavings = new ArrayList<PendingTransaction>();
    	    List<CompletedTransaction> completedSavings =  new ArrayList<CompletedTransaction>();
    	  
    	    if (savings != null ) {
    	    	pendingSavings = sdao.pendingTransactions(savings.getAccountNumber());
    	    	completedSavings = sdao.completedTransactions(savings.getAccountNumber());
     		}
    		 	    
            int p1 = pendingSavings.size(); 
            int p2 = completedSavings.size(); 

            // step 1
            Document document = new Document();
            // step 2
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            // step 3
            document.open();
            // step 4
            document.add(new Paragraph("Here are your pending savings transactions:"));
            
            PdfPTable table = new PdfPTable(6);
            table.addCell("Initiator ID");
            table.addCell("Amount");
            table.addCell("Time stamp");
            table.addCell("To account ID");
            table.addCell("Description");
            table.addCell("From Account Id");
            
            for(int aw = 0; aw < p1; aw++){
                table.addCell(String.valueOf(pendingSavings.get(aw).getInitiatorID()));
                table.addCell(String.valueOf(pendingSavings.get(aw).getAmount()));
                table.addCell(String.valueOf(pendingSavings.get(aw).getStamp()));
                table.addCell(String.valueOf(pendingSavings.get(aw).getToAccountID()));
                table.addCell(String.valueOf(pendingSavings.get(aw).getDescription()));
                table.addCell(String.valueOf(pendingSavings.get(aw).getFromAccountID()));
               
            }
            document.add(table);
            
            document.add(new Paragraph("Here are your completed savings transactions:"));
            
            PdfPTable table1 = new PdfPTable(9);
            table1.addCell("Initiator ID");
            table1.addCell("Amount");
            table1.addCell("Initiated Time");
            table1.addCell("To Account ID");
            table1.addCell("Description");
            table1.addCell("From Account ID");
            table1.addCell("Get Cmmpleted Time");
            table1.addCell("Approved by");
            table1.addCell("status");
            
            for(int aw=0; aw<completedSavings.size();aw++)
            {
            	table1.addCell(String.valueOf(completedSavings.get(aw).getInitiatorID()));
                table1.addCell(String.valueOf(completedSavings.get(aw).getAmount()));
                table1.addCell(String.valueOf(completedSavings.get(aw).getStamp()));
                table1.addCell(String.valueOf(completedSavings.get(aw).getToAccountID()));
                table1.addCell(String.valueOf(completedSavings.get(aw).getDescription()));
                table1.addCell(String.valueOf(completedSavings.get(aw).getFromAccountID()));
               
                table1.addCell(String.valueOf(completedSavings.get(aw).getCompletedStamp()));
                table1.addCell(String.valueOf(completedSavings.get(aw).getReviewerID()));
                table1.addCell(String.valueOf(completedSavings.get(aw).getStatus()));
            }
            
            document.add(table1);
            
            // step 5
            document.close();

            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
            ctx.close();
        }
        catch(DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }
	
	
	
	

	@RequestMapping("/customer/downloadCheckings")
    public void checkingStatement(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
        	 int userId=(Integer)request.getSession().getAttribute("userID");
        	 ClassPathXmlApplicationContext  ctx = new ClassPathXmlApplicationContext("DaoDetails.xml"); 
     	    CustomerDashboardDaoImpl sdao = ctx.getBean("customerDashboardDaoImpl" , CustomerDashboardDaoImpl.class);
 			
    	    CheckingAccount checking = sdao.checkingAccountDetails(userId);
     	    
     	    List<PendingTransaction> pendingChecking = new ArrayList<PendingTransaction>();
     	    List<CompletedTransaction> completedChecking =  new ArrayList<CompletedTransaction>();
     	  
     	    if (checking != null ) {
     	    	pendingChecking = sdao.pendingTransactions(checking.getAccountNumber());
     	    	completedChecking = sdao.completedTransactions(checking.getAccountNumber());
      		}
     		 	    
             int p1 = pendingChecking.size(); 
             int p2 = completedChecking.size(); 

             // step 1
             Document document = new Document();
             // step 2
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter.getInstance(document, baos);
             // step 3
             document.open();
             // step 4
             document.add(new Paragraph("Here are your pending checking transactions:"));
             
             PdfPTable table = new PdfPTable(6);
             table.addCell("Initiator ID");
             table.addCell("Amount");
             table.addCell("Time stamp");
             table.addCell("To account ID");
             table.addCell("Description");
             table.addCell("From Account Id");
             
             for(int aw = 0; aw < p1; aw++){
                 table.addCell(String.valueOf(pendingChecking.get(aw).getInitiatorID()));
                 table.addCell(String.valueOf(pendingChecking.get(aw).getAmount()));
                 table.addCell(String.valueOf(pendingChecking.get(aw).getStamp()));
                 table.addCell(String.valueOf(pendingChecking.get(aw).getToAccountID()));
                 table.addCell(String.valueOf(pendingChecking.get(aw).getDescription()));
                 table.addCell(String.valueOf(pendingChecking.get(aw).getFromAccountID()));
                
             }
             document.add(table);
             
             document.add(new Paragraph("Here are your completed savings transactions:"));
             
             PdfPTable table1 = new PdfPTable(9);
             table1.addCell("Initiator ID");
             table1.addCell("Amount");
             table1.addCell("Initiated Time");
             table1.addCell("To Account ID");
             table1.addCell("Description");
             table1.addCell("From Account ID");
             table1.addCell("Get Cmmpleted Time");
             table1.addCell("Approved by");
             table1.addCell("status");
             
             for(int aw=0; aw<completedChecking.size();aw++)
             {
             	table1.addCell(String.valueOf(completedChecking.get(aw).getInitiatorID()));
                 table1.addCell(String.valueOf(completedChecking.get(aw).getAmount()));
                 table1.addCell(String.valueOf(completedChecking.get(aw).getStamp()));
                 table1.addCell(String.valueOf(completedChecking.get(aw).getToAccountID()));
                 table1.addCell(String.valueOf(completedChecking.get(aw).getDescription()));
                 table1.addCell(String.valueOf(completedChecking.get(aw).getFromAccountID()));
                
                 table1.addCell(String.valueOf(completedChecking.get(aw).getCompletedStamp()));
                 table1.addCell(String.valueOf(completedChecking.get(aw).getReviewerID()));
                 table1.addCell(String.valueOf(completedChecking.get(aw).getStatus()));
             }
             
             document.add(table1);
             
             // step 5
             document.close();

             // setting some response headers
             response.setHeader("Expires", "0");
             response.setHeader("Cache-Control",
                 "must-revalidate, post-check=0, pre-check=0");
             response.setHeader("Pragma", "public");
             // setting the content type
             response.setContentType("application/pdf");
             // the contentlength
             response.setContentLength(baos.size());
             // write ByteArrayOutputStream to the ServletOutputStream
             OutputStream os = response.getOutputStream();
             baos.writeTo(os);
             os.flush();
             os.close();
             ctx.close();
         }
         catch(DocumentException e) {
             throw new IOException(e.getMessage());
         }

    }
	
	
	
	

	@RequestMapping("/customer/downloadCredit")
    public void creditStatement(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            // Get the text that will be added to the PDF
            int userId=(Integer)request.getSession().getAttribute("userID");
            ClassPathXmlApplicationContext  ctx = new ClassPathXmlApplicationContext("DaoDetails.xml"); 
    	    CustomerDashboardDaoImpl sdao = ctx.getBean("customerDashboardDaoImpl" , CustomerDashboardDaoImpl.class);
    	    CreditAccount credit = sdao.creditAccountDetails(userId);

    	    
    	    List<PendingTransaction> pendingCredit = new ArrayList<PendingTransaction>();
    	    List<CompletedTransaction> completedCredit = new ArrayList<CompletedTransaction>();
    	    
    	    if (credit != null ) {
    	    	pendingCredit = sdao.pendingTransactions(credit.getAccountNumber());
    	    	completedCredit = sdao.completedTransactions(credit.getAccountNumber());
     		}

            // step 1
            Document document = new Document();
            // step 2
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            // step 3
            document.open();
            // step 4
            document.add(new Paragraph("Here are your pending checking transactions:"));
            
            PdfPTable table = new PdfPTable(6);
            table.addCell("Initiator ID");
            table.addCell("Amount");
            table.addCell("Time stamp");
            table.addCell("To account ID");
            table.addCell("Description");
            table.addCell("From Account Id");
            
            for(int aw = 0; aw < pendingCredit.size(); aw++){
                table.addCell(String.valueOf(pendingCredit.get(aw).getInitiatorID()));
                table.addCell(String.valueOf(pendingCredit.get(aw).getAmount()));
                table.addCell(String.valueOf(pendingCredit.get(aw).getStamp()));
                table.addCell(String.valueOf(pendingCredit.get(aw).getToAccountID()));
                table.addCell(String.valueOf(pendingCredit.get(aw).getDescription()));
                table.addCell(String.valueOf(pendingCredit.get(aw).getFromAccountID()));
               
            }
            document.add(table);
            
            document.add(new Paragraph("Here are your completed savings transactions:"));
            
            PdfPTable table1 = new PdfPTable(9);
            table1.addCell("Initiator ID");
            table1.addCell("Amount");
            table1.addCell("Initiated Time");
            table1.addCell("To Account ID");
            table1.addCell("Description");
            table1.addCell("From Account ID");
            table1.addCell("Get Cmmpleted Time");
            table1.addCell("Approved by");
            table1.addCell("status");
            
            for(int aw=0; aw<completedCredit.size();aw++)
            {
            	table1.addCell(String.valueOf(completedCredit.get(aw).getInitiatorID()));
                table1.addCell(String.valueOf(completedCredit.get(aw).getAmount()));
                table1.addCell(String.valueOf(completedCredit.get(aw).getStamp()));
                table1.addCell(String.valueOf(completedCredit.get(aw).getToAccountID()));
                table1.addCell(String.valueOf(completedCredit.get(aw).getDescription()));
                table1.addCell(String.valueOf(completedCredit.get(aw).getFromAccountID()));
               
                table1.addCell(String.valueOf(completedCredit.get(aw).getCompletedStamp()));
                table1.addCell(String.valueOf(completedCredit.get(aw).getReviewerID()));
                table1.addCell(String.valueOf(completedCredit.get(aw).getStatus()));
            }
            
            document.add(table1);
            
            // step 5
            document.close();

            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
            ctx.close();
        }
        catch(DocumentException e) {
            throw new IOException(e.getMessage());
        }
	}
}