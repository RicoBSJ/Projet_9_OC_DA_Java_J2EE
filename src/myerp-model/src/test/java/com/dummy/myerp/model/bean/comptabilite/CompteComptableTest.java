package com.dummy.myerp.model.bean.comptabilite;

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
        CompteComptable.getByNumero(list, 1);
    }

    /*
    INSERT INTO MYERP.compte_comptable (numero, libelle) VALUES (	401,	'Fournisseurs'	);
	INSERT INTO MYERP.compte_comptable (numero, libelle) VALUES (	411,	'Clients'	);
	INSERT INTO MYERP.compte_comptable (numero, libelle) VALUES (	4456,	'Taxes sur le chiffre d''affaires déductibles'	);
	INSERT INTO MYERP.compte_comptable (numero, libelle) VALUES (	4457,	'Taxes sur le chiffre d''affaires collectées par l''entreprise'	);
	INSERT INTO MYERP.compte_comptable (numero, libelle) VALUES (	512,	'Banque'	);
	INSERT INTO MYERP.compte_comptable (numero, libelle) VALUES (	606,	'Achats non stockés de matières et fournitures'	);
	INSERT INTO MYERP.compte_comptable (numero, libelle) VALUES (	706,	'Prestations de services'	);
     */

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
