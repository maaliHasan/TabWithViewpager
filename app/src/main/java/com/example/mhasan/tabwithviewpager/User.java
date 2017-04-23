package com.example.mhasan.tabwithviewpager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mhasan on 4/18/2017.
 * User
 */

public class User implements Serializable {
    public String firstName;
    public String lastName;
    public String title;
    public int id;
    public String profilePic;
    public String description;
    public ArrayList<String> images = new ArrayList<String>();
    public String fullName;
}

