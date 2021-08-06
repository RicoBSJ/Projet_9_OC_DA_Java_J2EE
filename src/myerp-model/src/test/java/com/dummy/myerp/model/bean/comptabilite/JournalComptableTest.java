package com.dummy.myerp.model.bean.comptabilite;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JournalComptableTest {

    @Test
    public void getByCode() {

        JournalComptable journalComptable1 = new JournalComptable();
        journalComptable1.setCode("AC");
        JournalComptable journalComptable2 = new JournalComptable();
        journalComptable2.setCode("VE");
        JournalComptable journalComptable3 = new JournalComptable();
        journalComptable3.setCode("AC");
        JournalComptable journalComptable4 = new JournalComptable();
        journalComptable4.setCode("OD");
        List<String> list = new ArrayList<>();
        list.add(journalComptable1.getCode());
        list.add(journalComptable2.getCode());
        list.add(journalComptable3.getCode());
        list.add(journalComptable4.getCode());
        Assertions.assertThat(list.contains("OC")).isFalse();
    }
}
