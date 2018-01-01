package my_package;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.NoSuchElementException;

public class MyFileDAO implements MyDAO{
    public int check;
    public MyFileDAO(@NotNull final File dir) {

        this.dir = dir;
    }

    @NotNull

    private final File dir;
    public File getFile (@NotNull final String key) {
        return  new File(dir, key);
    }


    @NotNull
    @Override
    public byte[] get(@NotNull final String key) throws NoSuchElementException, IllegalArgumentException, IOException{

        final File file = getFile(key);
        final byte[] value = new byte[(int)file.length()];
        try (InputStream is = new FileInputStream(file)) {

            if(is.read(value) != value.length) {
                throw new IOException("Can't read file");
            }

        }
        return value;
    }

    @Override
    public  void upsert(@NotNull final String key,  final byte[] value) throws IllegalArgumentException, IOException {

        try (OutputStream os = new FileOutputStream(getFile(key))){
            os.write(value);
        }

    }

    @Override
    public  void delete(@NotNull  String key) throws IllegalArgumentException, IOException{

        getFile(key).delete();
    }

    @Override
    public boolean check(@NotNull final String key ) {

        if ( getFile(key).exists() == true ) return true;
        else return false;
    }

}
