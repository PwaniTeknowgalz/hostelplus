package ke.co.hostelplus.hostelplus.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by  on 18/04/2017.
 */
@Table(name = "HostelDb")
public class HostelDb extends Model{

    @Column(name = "data")
    String data;

    @Column(name = "createdat")
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

    public HostelDb() {
        super();
    }

    public static List<HostelDb> getAll() {
        return new Select()
                .from(HostelDb.class)
                .execute();
    }
}
