package model;

import java.util.Objects;

public class Client {
    private String name;
    private String email;
    private String phone;
    private String cnp;

    public Client(String name, String email, String phone, String cnp) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cnp = cnp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(cnp, client.cnp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnp);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCnp() { return cnp; }
    public void setCnp(String cnp) { this.cnp = cnp; }

    @Override
    public String toString() {
        return "Client: " + name + " | Email: " + email + " | Tel: " + phone + " | CNP: " + cnp;
    }
}
