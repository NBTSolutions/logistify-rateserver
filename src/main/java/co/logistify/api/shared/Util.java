package co.logistify.api.shared;

public abstract class Util {

    public static boolean isCanadianZip(String zip) {
        return zip.matches("^[ABCEGHJKLMNPRSTVXY]{1}\\d{1}[A-Z]{1} *\\d{1}[A-Z]{1}\\d{1}$");
    }
    public static boolean isAmericanZip(String zip) {
        return zip.matches("^\\d{5}(-\\d{4})?$");
    }

}
