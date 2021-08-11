package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.validation.*;

public class ComptabiliteManagerImplTest {

    private final ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitTest() throws Exception {
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        /*Dans le code d'origine, la référence n'est pas setté
        Rajout du setReference au format code du journal dans lequel figure l'écriture,
        suivi de l'année et d'un numéro de séquence*/
        pEcritureComptable.setReference("AC-2019/00001");
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        manager.checkEcritureComptableUnit(pEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableTest() throws Exception {
        this.checkEcritureComptableUnitTest();
        this.checkEcritureComptableContextTest();
    }

    protected Validator getConstraintValidator() {
        Configuration<?> vConfiguration = Validation.byDefaultProvider().configure();
        ValidatorFactory vFactory = vConfiguration.buildValidatorFactory();
        return vFactory.getValidator();
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolationTest() throws Exception {
        EcritureComptable pEcritureComptable;
        pEcritureComptable = new EcritureComptable();
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                    new ConstraintViolationException(
                            "L'écriture comptable ne respecte pas les contraintes de validation",
                            vViolations));
        }
    }

    @Test
    public void checkEcritureComptableUnitRG2Test() {
        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null, new BigDecimal(123)));

        if (!pEcritureComptable.isEquilibree()) {
            throw new AssertionError("L'écriture comptable n'est pas équilibrée.");
        }
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3Test() throws Exception {
        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        pEcritureComptable.setReference("AC-2019/00001");
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));

        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }
        // On test le nombre de lignes car si l'écriture à une seule ligne
        // avec un montant au débit et un montant au crédit ce n'est pas valable
        if (pEcritureComptable.getListLigneEcriture().size() < 2
                || vNbrCredit < 1
                || vNbrDebit < 1) {
            throw new FunctionalException(
                    "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5Test() throws Exception {

        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        pEcritureComptable.setReference("AC-2019/00001");
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        String date = String.valueOf(pEcritureComptable.getDate());
        String anneeEcriture = date.substring(3);
        String reference = pEcritureComptable.getReference();
        String anneeRef = reference.substring(3, 7);
        String code = reference.substring(0,2);

        if(!anneeEcriture.equals(anneeRef)){
            throw new FunctionalException("L'année dans la référence ne correspond pas à la date de l'écriture");
        }

        // Vérification du Code du journal et de celui spécifié dans la référence

        if (!pEcritureComptable.getJournal().getCode().equals(code)) {
            throw new FunctionalException("Le code du journal spécifié dans la référence ne correspond pas");
        }
    }

    @Test
    public void checkEcritureComptableContextTest() {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        EcritureComptable pEcritureComptable1 = new EcritureComptable();
        pEcritureComptable1.setReference("AC-2019/00001");
        EcritureComptable pEcritureComptable2 = new EcritureComptable();
        pEcritureComptable2.setReference("OD-2019/00001");
        EcritureComptable pEcritureComptable3 = new EcritureComptable();
        pEcritureComptable3.setReference("AC-2019/00001");
        EcritureComptable pEcritureComptable4 = new EcritureComptable();
        pEcritureComptable4.setReference("BQ-2019/00001");
        ArrayList<String> list = new ArrayList<>();
        list.add(pEcritureComptable1.getReference());
        list.add(pEcritureComptable2.getReference());
        list.add(pEcritureComptable3.getReference());
        list.add(pEcritureComptable4.getReference());
        Assertions.assertThat(Collections.frequency(list, "AC-2019/00001")).isGreaterThan(1);
    }

    @Test
    public void addReferenceTest() {
        EcritureComptable pEcritureComptable = new EcritureComptable();
        pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        pEcritureComptable.setDate(new Date());
        pEcritureComptable.setLibelle("Libelle");
        pEcritureComptable.setReference("AC-2019/00001");
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        String currentYear = String.valueOf(pEcritureComptable.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
        String vRef = pEcritureComptable.getJournal().getCode() + "-" + currentYear + "/";

        if (pEcritureComptable.getReference().contains(currentYear)) {
            String sequence = pEcritureComptable.getReference().substring(8);
            Integer sequencenb = Integer.parseInt(sequence) + 1;
            vRef += String.format("%05d", sequencenb);
        } else {
            vRef += "00001";
        }
        pEcritureComptable.setReference(vRef);
    }
}
