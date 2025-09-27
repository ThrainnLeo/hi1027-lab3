public class TitleNotUniqueException extends RuntimeException {

    public TitleNotUniqueException() {

    }

    public TitleNotUniqueException(String message) {
        super(message); //kanske använda denna får se senare
    }
}

/*
 * Denna klass ska användas när en titel som inte är unik försöker
 * läggas till
 */
