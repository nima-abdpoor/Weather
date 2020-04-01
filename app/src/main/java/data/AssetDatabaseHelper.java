package data;




        import android.content.Context;
        import android.util.Log;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;

public class AssetDatabaseHelper {

    private Context context;
    private String dbName = "db_city";
    public AssetDatabaseHelper(Context context){
        this(context, "db_city");
    }

    public AssetDatabaseHelper(Context context, String dbName){
        this.context = context;
        this.dbName = dbName;
    }

    public void checkDb(){
        File dbfile = context.getDatabasePath(dbName);
        if(! dbfile.exists()){
            try {
                copyDatabase(dbfile);
                Log.i("AssetsDatabaseHelper", "database copied.");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database." , e);
            }
        }
    }

    public void copyDatabase(File dbfile) throws IOException{
        InputStream is = context.getAssets().open(dbName);
        dbfile.getParentFile().mkdirs();
        OutputStream os = new FileOutputStream(dbfile);

        int len = 0;
        byte[] buffer = new byte[1024];

        while((len = is.read(buffer)) > 0){
            os.write(buffer, 0, len);
        }
        os.flush();
        os.close();
        is.close();
    }


}
