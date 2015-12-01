package com.projects.model;

/**
 * Created by Lazarenko on 06.11.2015.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
@Table(name = "business")
public class Business implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Business() {
    }

    public Business(String name) {
        this.name = name;
    }

    @Column(name = "NAME")
    private String name;

    @Column(name = "ISDO")
    private Boolean isDo;

    @Column(name = "ENDDATE")
    private String endDate;

    @Column(name = "NOTE")
    private String note;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getDo() {
        return isDo;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getNote() {
        return note;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDo(Boolean aDo) {
        isDo = aDo;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setNote(String note) {
        this.note = note;
    }

    //for select terminate business
    public boolean isTerminate() throws ParseException {

        boolean flag = false;

        if (!isDo) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar dateAct = Calendar.getInstance();

            Calendar date = Calendar.getInstance();
            date.setTime(sdf.parse(endDate));

            int yearDif = date.get(Calendar.YEAR) - dateAct.get(Calendar.YEAR);

            if (yearDif < 0)
                flag = true;
            else if (yearDif > 0)
                flag = false;
            else {
                int monthDif = date.get(Calendar.MONTH) - dateAct.get(Calendar.MONTH);
                if (monthDif < 0)
                    flag = true;
                else if (monthDif > 0)
                    flag = false;
                else {
                    int dayDif = date.get(Calendar.DAY_OF_MONTH) - dateAct.get(Calendar.DAY_OF_MONTH);
                    if ((dayDif == 0) || (dayDif == 1) || (dayDif < 0))
                        flag = true;
                    else if (dayDif > 0)
                        flag = false;
                }
            }
        }
        return flag;
    }
}


