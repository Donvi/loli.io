package io.loli.sc.server.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "ClientToken.findByUId", query = "SELECT c FROM ClientToken c WHERE c.user.id=:uid"),
        @NamedQuery(name = "ClientToken.findByToken", query = "SELECT c FROM ClientToken c WHERE c.token=:token") })
public class ClientToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    private User user;
    @Column
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
