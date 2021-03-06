package pl.calculator.string;

import pl.calculator.plugins.PluginList;

import java.util.ArrayList;

public class ProcessString {
    private String string;
    private PluginList ppl;
    private String StringSuma;

    public ProcessString(String s, PluginList pl) {
        if (s.isEmpty()) throw new IllegalArgumentException("Nie ma czego liczyć :)");
        this.string = s;
        this.ppl = pl;
        run();
    }

    public double getSum() {
        return Double.parseDouble(this.StringSuma);
    }

    private void run() {
        //JEDNOARGUMENTOWE
        validity5();
        //JEDNOARGUMENTOWE SA GOTOWE

        //DWUARGUMENTOWY WAZNOSC 4
        validity4();
        //DWUARGUMENTOWE WAZNOSC c2
        validity2();
        //przetworzony string -> onp -> wylicz
        //sprawdzamy wczesniej czy reszta sie zgadza
        new EntryGuard().process(this.string, ppl.getOperands());
        this.StringSuma = new ONPCalculate(new ONP(this.string).getResult()).getResult();
    }

    public String getResultString() {
        return StringSuma;
    }

    private void validity5() {
        ArrayList<String> oneArgumentOperations = new ArrayList<>();
        ppl.getOb().forEach((k, v) -> {
            if (v.getValidity() >= 5) {
                oneArgumentOperations.add(v.getSign());
            }
        });
        for (String s : oneArgumentOperations) {
            int index = this.string.indexOf(s);
            if (index != -1) {
                int first = index + s.length();
                int last = this.string.substring(first).indexOf(")") + first;
                String tmp = this.string.substring(first + 1, last);
                double liczba = Double.parseDouble(tmp);
                double suma = this.ppl.getObOne(s).action(liczba);
                this.string = this.string.substring(0, index) + suma + this.string.substring(last + 1);
            }
        }
    }

    private void validity4() {
        //wiadomo ze tutaj juz sa jednoargumentowe wyrazenia oraz dwu skladnikowe
        ArrayList<String> val4 = new ArrayList<>();
        ArrayList<String> goodOperand = new ArrayList<>();
        ppl.getOb().forEach((k, v) -> {
            if (v.getValidity() == 4) {
                val4.add(v.getSign());
            }
            if (v.getValidity() <= 4) {
                goodOperand.add(v.getSign());
            }
        });
        check(goodOperand);
        operations(val4, goodOperand);


    }

    private void operations(ArrayList<String> val, ArrayList<String> goodOperand) {
        for (String sign : val) {
            int index = this.string.indexOf(sign);
            while (index != -1) {
                //znalazlo znak
                //wez liczbe przed
                boolean szukam = true;
                int copyIndex = index;
                while (szukam) {
                    copyIndex = copyIndex - 1;
                    for (String s : goodOperand) {
                        if (this.string.charAt(copyIndex) == s.charAt(0)) {
                            szukam = false;
                            break;
                        }
                    }
                    if (copyIndex <= 0) {
                        copyIndex = 0;
                        szukam = false;
                    }
                }
                String liczbaA;
                if (copyIndex == 0) {
                    liczbaA = this.string.substring(0, index);
                } else {
                    liczbaA = this.string.substring(copyIndex + 1, index);
                }
                //wez liczbe po
                szukam = true;
                int copyIndex2 = index + 1;
                while (szukam) {

                    for (String s : goodOperand) {
                        if (this.string.charAt(copyIndex2) == s.charAt(0)) {
                            szukam = false;
                            break;
                        }
                    }
                    copyIndex2 = copyIndex2 + 1;
                    if (copyIndex2 >= this.string.length()) {
                        szukam = false;
                    }
                }

                String liczbaB;
                if (copyIndex2 >= this.string.length()) {
                    liczbaB = this.string.substring(index + 1);
                } else {
                    liczbaB = this.string.substring(index + 1, copyIndex2 - 1);
                }

                double a = Double.parseDouble(liczbaA);
                double b = Double.parseDouble(liczbaB);
                double suma = ppl.getObOne(sign).action(a, b);
                if (copyIndex != 0) {
                    copyIndex += 1;
                }
                if (copyIndex2 < this.string.length()) {
                    copyIndex2 -= 1;
                }
                this.string = this.string.substring(0, copyIndex) + suma + this.string.substring(copyIndex2);
                index = this.string.indexOf(sign);
            }
            //nie ma indeksu leci do nastepnego znaku
        }
    }

    private void validity2() {
        //wiadomo ze tutaj juz sa jednoargumentowe wyrazenia oraz dwu skladnikowe
        ArrayList<String> val2 = new ArrayList<>();
        ArrayList<String> goodOperand = new ArrayList<>();
        ppl.getOb().forEach((k, v) -> {
            if (v.getValidity() == 2) {
                val2.add(v.getSign());
            }
            if (v.getValidity() <= 2) {
                goodOperand.add(v.getSign());
            }
        });
        operations(val2, goodOperand);


    }

    private void check(ArrayList<String> op) {
        for (String o : op) {
            if (this.string.charAt(0) == o.charAt(0)) {
                throw new IllegalArgumentException("Proszę wpisać poprawne wyrażenie");
            }
        }
    }

}
