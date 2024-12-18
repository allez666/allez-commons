package com.allez.application.wrapper;



import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */

/**
 * @author chenyu
 * @date 2024/12/16 19:28
 * @description
 * 从mvc 源码中复制出来
 * @see org.springframework.web.servlet.function.DefaultServerRequestBuilder.BodyInputStream
 * 因为是私有类，也可以用反射去拿。
 * Class<?> clazz = Class.forName("org.springframework.web.servlet.function.DefaultServerRequestBuilder$BodyInputStream");
 * Constructor<?> bodyInputStream = clazz.getDeclaredConstructor(byte[].class);
 * bodyInputStream.setAccessible(true);
 * bodyInputStream.newInstance();
 */
public class BodyInputStreamWrapper extends ServletInputStream {

    private final InputStream delegate;

    public BodyInputStreamWrapper(byte[] body) {
        this.delegate = new ByteArrayInputStream(body);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }


    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
        return this.delegate.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.delegate.read(b, off, len);
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.delegate.read(b);
    }

    @Override
    public long skip(long n) throws IOException {
        return this.delegate.skip(n);
    }

    @Override
    public int available() throws IOException {
        return this.delegate.available();
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        this.delegate.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        this.delegate.reset();
    }

    @Override
    public boolean markSupported() {
        return this.delegate.markSupported();
    }
}