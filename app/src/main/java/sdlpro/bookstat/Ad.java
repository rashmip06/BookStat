package sdlpro.bookstat;

public class Ad {
    private String AdId;
    private String Descp;
    private String url;
    private String UD;
    private String Amount;
    public Ad() {

    }

    public void setAdId(String adId) {
        AdId = adId;
    }


    public void setDescp(String descp) {
        Descp = descp;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUD(String UD) {
        this.UD = UD;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getAdId() {
        return AdId;
    }

    public String getDescp() {
        return Descp;
    }

    public String getUrl() {
        return url;
    }

    public String getUD() {
        return UD;
    }

    public String getAmount() {
        return Amount;
    }
}