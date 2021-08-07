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
        CompteComptable compteComptable2 = new CompteComptable();
        compteComptable2.setNumero(411);
        CompteComptable compteComptable3 = new CompteComptable();
        compteComptable3.setNumero(401);
        CompteComptable compteComptable4 = new CompteComptable();
        compteComptable4.setNumero(4456);
        List<CompteComptable> list = new ArrayList<>();
        list.add(compteComptable1);
        list.add(compteComptable2);
        list.add(compteComptable3);
        list.add(compteComptable4);
        CompteComptable vRetour = null;
        for (CompteComptable vBean : list) {
            if (vBean != null && vBean.getNumero().equals(compteComptable4.getNumero())) {
                vRetour = vBean;
                break;
            }
        }
        Assertions.assertThat(vRetour == compteComptable4).isTrue();
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
