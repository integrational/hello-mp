package org.integrational.helidon.hellomp;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        io.helidon.microprofile.server.Main.main(args);
    }

    public static int serverPort() {
        return io.helidon.microprofile.server.Main.serverPort();
    }
}
