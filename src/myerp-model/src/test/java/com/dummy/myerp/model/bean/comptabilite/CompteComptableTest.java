package com.dummy.myerp.model.bean.comptabilite;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;

public class CompteComptableTest {

    @Test
    public void getByNumeroTest() {

        CompteComptable compteComptable1 = new CompteComptable();
        compteComptable1.setNumero(606);
        compteComptable1.setLibelle("AC");
        CompteComptable compteComptable2 = new CompteComptable();
        compteComptable2.setNumero(706);
        compteComptable2.setLibelle("VE");
        CompteComptable compteComptable3 = new CompteComptable();
        compteComptable3.setNumero(512);
        compteComptable3.setLibelle("BQ");
        CompteComptable compteComptable4 = new CompteComptable();
        compteComptable4.setNumero(401);
        compteComptable4.setLibelle("OD");
        ArrayList<CompteComptable> list = new ArrayList<>();
        list.add(compteComptable1);
        list.add(compteComptable2);
        list.add(compteComptable3);
        list.add(compteComptable4);
        CompteComptable fourthNumber = list.get(3);
        Assertions.assertThat(CompteComptable.getByNumero(list, 401)).isEqualTo(fourthNumber);
    }

    /*
    public static CompteComptable getByNumero(List<? extends CompteComptable> pList, Integer pNumero) {
        CompteComptable vRetour = null;
        for (CompteComptable vBean : pList) {
            if (vBean != null && Objects.equals(vBean.getNumero(), pNumero)) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }
     */
}
