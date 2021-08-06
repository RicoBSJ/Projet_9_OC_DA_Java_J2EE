package com.dummy.myerp.model.bean.comptabilite;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CompteComptableTest {

    @Test
    public void getByNumero() {

        CompteComptable compteComptable1 = new CompteComptable();
        compteComptable1.setNumero(401);
        CompteComptable compteComptable2 = new CompteComptable();
        compteComptable2.setNumero(411);
        CompteComptable compteComptable3 = new CompteComptable();
        compteComptable3.setNumero(401);
        CompteComptable compteComptable4 = new CompteComptable();
        compteComptable4.setNumero(4456);
        List<Integer> list = new ArrayList<>();
        list.add(compteComptable1.getNumero());
        list.add(compteComptable2.getNumero());
        list.add(compteComptable3.getNumero());
        list.add(compteComptable4.getNumero());
        Assertions.assertThat(list.contains(4454)).isFalse();
    }
}
