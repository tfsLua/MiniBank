package com.squad21.pitang.User.Manager.ManagerModel;
import com.squad21.pitang.User.User;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "Manager")
public class MnModel extends User {
}
