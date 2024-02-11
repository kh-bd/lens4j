package cases.field_strategy.different_package.other;

public class Account {
    private String privateNumber;
    public String publicNumber;
    String packageNumber;
    protected String protectedNumber;

    public String getPrivateNumber() {
        return privateNumber;
    }

    public void setPrivateNumber(String privateNumber) {
        this.privateNumber = privateNumber;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getProtectedNumber() {
        return protectedNumber;
    }

    public void setProtectedNumber(String protectedNumber) {
        this.protectedNumber = protectedNumber;
    }
}