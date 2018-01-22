package lkonina;

import lkonina.MyDAO;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.io.File;
import java.util.NoSuchElementException;
import java.lang.String;

public class MyFileDAO implements MyDAO {

    public MyFileDAO(@NotNull final File dir) {
        this.dir = dir;
    }

    private final File dir;

    public File getFile(@NotNull final String key) {
        return new File(dir, key);
    }

    @NotNull
    @Override
    public byte[] get(@NotNull String key) throws NoSuchElementException, IllegalArgumentException, IOException {

        final File file = getFile(key);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            if (!file.exists()) {
                throw new IOException("File doesn't exist");
            }
            int data;
            while ((data = is.read()) != -1) {
                os.write(data);
            }
        }
        return os.toByteArray();
    }

    @Override
    public void upsert(@NotNull final String key, final byte[] value) throws IllegalArgumentException, IOException {

        try (OutputStream os = new FileOutputStream(getFile(key))) {
            os.write(value);
        }
    }

    @Override
    public void delete(@NotNull String key) throws IllegalArgumentException, IOException {
        getFile(key).delete();
    }

    @Override
    public boolean check(@NotNull final String key) {
        return getFile(key).exists();
    }
}
