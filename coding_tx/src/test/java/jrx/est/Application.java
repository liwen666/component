//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package jrx.est;


import jdk.nashorn.internal.runtime.Timing;
import org.wildfly.common.lock.Locks;

import java.io.Closeable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public abstract class Application implements Closeable {
    public static final String APP_CLASS_NAME = "io.quarkus.runner.ApplicationImpl";
    private static final int ST_INITIAL = 0;
    private static final int ST_STARTING = 1;
    private static final int ST_STARTED = 2;
    private static final int ST_STOPPING = 3;
    private static final int ST_STOPPED = 4;
    private final Lock stateLock = Locks.reentrantLock();
    private final Condition stateCond;
    private int state;
    private static volatile Application currentApplication;

    protected Application() {
        this.stateCond = this.stateLock.newCondition();
        this.state = 0;
    }

    public final void start(String[] args) {
        currentApplication = this;
        Lock stateLock = this.stateLock;
        stateLock.lock();

        try {
            label201:
            while (true) {
                switch (this.state) {
                    case 0:
                        this.state = 1;
                        break label201;
                    case 1:
                        try {
                            this.stateCond.await();
                            break;
                        } catch (InterruptedException var30) {
                            Thread.currentThread().interrupt();
                            throw interruptedOnAwaitStart();
                        }
                    case 2:
                        return;
                    default:
                        throw new IllegalStateException("The application is stopping");
                }
            }
        } finally {
            stateLock.unlock();
        }

    }

    public static Application currentApplication() {
        return currentApplication;
    }

    protected abstract void doStop();

    public abstract String getName();

    private static IllegalStateException interruptedOnAwaitStart() {
        return new IllegalStateException("Interrupted while waiting for another thread to start the application");
    }

    private static IllegalStateException interruptedOnAwaitStop() {
        return new IllegalStateException("Interrupted while waiting for another thread to stop the application");
    }

    public void awaitShutdown() {
        Lock stateLock = this.stateLock;
        stateLock.lock();

        try {
            while(true) {
                switch(this.state) {
                case 4:
                    return;
                default:
                    try {
                        this.stateCond.await();
                    } catch (InterruptedException var6) {
                        Thread.currentThread().interrupt();
                        throw interruptedOnAwaitStop();
                    }
                }
            }
        } finally {
            stateLock.unlock();
        }
    }

    public boolean isStarted() {
        return this.state == 2;
    }
}
