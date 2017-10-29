package com.group10.controllers.customer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;

import com.group10.dbmodels.CompletedTransaction;

public class DownloadTransactions {
	public static void createFile(List<CompletedTransaction> data, String fileName) throws IOException{		
		
		File transactions = new File("/customerStatements", fileName);
		
		FileUtils.writeLines(transactions, data);
	}
}
