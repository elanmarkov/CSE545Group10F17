
public class DataMaskUnmask {
	public DataMaskUnmask() {
		
	}
	// Different masking based on the type of input.
	// Int structure given below, there will be similar function for any other
	// input structure.
	public int mask(int valToMask) {
		return valToMask + 1;
	}
	public int unMask(int valToUnmask) {
		return valToUnmask - 1;
	}
}
