package ke.co.hostelplus.hostelplus.db;

/**
 * Created by  on 18/04/2017.
 */

public class HostelData {

    String data;
    String createdat;

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public HostelData(String data, String createdat) {
        this.data = data;
        this.createdat = createdat;
    }

    public HostelData() {
    }
}
