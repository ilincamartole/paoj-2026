package com.pao.laboratory10.exercise2;
import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;



import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Tranzactie> lista = new ArrayList<>();


        int N = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < N; i++) {
            int id = sc.nextInt();
            double suma = sc.nextDouble();
            String data = sc.next();
            TipTranzactie tip = TipTranzactie.valueOf(sc.next());

            lista.add(new Tranzactie(id, suma, data, tip));
        }

        sc.nextLine();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            String cmd = parts[0];

            switch (cmd) {

                case "UNIQUE_IDS": {
                    LinkedHashSet<Integer> ids = new LinkedHashSet<>();

                    for (Tranzactie t : lista) {
                        ids.add(t.getId());
                    }

                    System.out.println(
                            "IDs unice (" + ids.size() + "): " + ids
                    );
                    break;
                }

                case "MONTHLY_REPORT": {
                    TreeMap<String, double[]> raport = new TreeMap<>();

                    for (Tranzactie t : lista) {
                        String luna = t.getData().substring(0, 7);

                        raport.putIfAbsent(luna, new double[2]);

                        if (t.getTip() == TipTranzactie.CREDIT) {
                            raport.get(luna)[0] += t.getSuma();
                        } else {
                            raport.get(luna)[1] += t.getSuma();
                        }
                    }

                    for (String luna : raport.keySet()) {
                        double credit = raport.get(luna)[0];
                        double debit = raport.get(luna)[1];

                        System.out.printf(
                                "%s: CREDIT %.2f RON, DEBIT %.2f RON%n",
                                luna, credit, debit
                        );
                    }
                    break;
                }

                case "TOP": {
                    int n = Integer.parseInt(parts[1]);

                    ArrayList<Tranzactie> copie =
                            new ArrayList<>(lista);

                    copie.sort(
                            Comparator.comparingDouble(
                                    Tranzactie::getSuma
                            ).reversed()
                    );

                    System.out.println("Top " + n + ":");

                    for (int i = 0; i < Math.min(n, copie.size()); i++) {
                        System.out.println(copie.get(i));
                    }
                    break;
                }

                case "SORT_ASC": {
                    Collections.sort(
                            lista,
                            Comparator.comparingDouble(
                                    Tranzactie::getSuma
                            )
                    );

                    for (Tranzactie t : lista) {
                        System.out.println(t);
                    }
                    break;
                }

                case "SORT_DESC": {
                    Collections.sort(
                            lista,
                            Comparator.comparingDouble(
                                    Tranzactie::getSuma
                            ).reversed()
                    );

                    for (Tranzactie t : lista) {
                        System.out.println(t);
                    }
                    break;
                }

                case "REVERSE": {
                    Collections.reverse(lista);

                    for (Tranzactie t : lista) {
                        System.out.println(t);
                    }
                    break;
                }

                case "MIN_MAX": {
                    Comparator<Tranzactie> cmp =
                            Comparator.comparingDouble(
                                    Tranzactie::getSuma
                            );

                    Tranzactie min = Collections.min(lista, cmp);
                    Tranzactie max = Collections.max(lista, cmp);

                    System.out.println("MIN: " + min);
                    System.out.println("MAX: " + max);
                    break;
                }

                case "CME_DEMO": {
                    try {
                        for (Tranzactie t : lista) {
                            lista.remove(t);
                        }
                    } catch (ConcurrentModificationException e) {
                        System.out.println(
                                "ConcurrentModificationException prins: modificare in iteratie detectata."
                        );
                    }
                    break;
                }
            }
        }

        sc.close();
    }
}
