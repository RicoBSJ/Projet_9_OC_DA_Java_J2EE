package com.dummy.myerp.model.bean.comptabilite;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CompteComptableTest {

    @Test
    public void getByNumeroTest() {
        CompteComptable compteComptable1 = new CompteComptable();
        compteComptable1.setNumero(401);
        compteComptable1.setLibelle("Fournisseurs");
        CompteComptable compteComptable2 = new CompteComptable();
        compteComptable2.setNumero(411);
        compteComptable1.setLibelle("Clients");
        CompteComptable compteComptable3 = new CompteComptable();
        compteComptable3.setNumero(401);
        compteComptable3.setLibelle("Fournisseurs");
        CompteComptable compteComptable4 = new CompteComptable();
        compteComptable4.setNumero(4456);
        compteComptable4.setLibelle("Taxes sur le chiffre d''affaires déductibles");
        List<CompteComptable> list = new ArrayList<>();
        list.add(compteComptable1);
        list.add(compteComptable2);
        list.add(compteComptable3);
        list.add(compteComptable4);
        Assertions.assertThat(CompteComptable.getByNumero(list, 4455)).isNull();
    }
}
