package main;

public class Message {

    public enum Status{
        RECIVED,
        PENDING,
        EMPTY;
    }

    public enum Type{
        NULL;
    }

    public Status status;
    private String message;

    public Message(String message){
        this.message = message;
        this.status = Status.PENDING;
    }
    public Message(Status status){
        this.status = status;
        if(status == Status.EMPTY){
            this.message = "";
        }
    }

    public String get(){
        return message;
    }

    public void set(String message){
        this.message = message;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
