/*
Tarefa 1 - sumarizar para cada plataforma:

+ número de reviews desta plataforma
+ percentual de ‘Great’ reviews (sobre o número de reviews desta plataforma)
+ média aritmética dos scores
+ desvio padrão populacional dos scores
+ melhor jogo (basta indicar um entre os de maior score)
+ pior jogo (basta indicar um entre os de menor score)

Ao final: qual a plataforma com os jogos do gênero ‘Racing’ mais bem avaliados?
 */

/**
 *
 * @author andersondalcorso
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javafx.application.Platform;

public class ReadCsv {

    public static void main(String[] args) {

        String csvFile = "src/aula/game-reviews.csv";
        String line;
        String cvsSplitBy = ";";

        TreeMap<String, ArrayList<Data>> map = new TreeMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            line = br.readLine();

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] ln = line.split(cvsSplitBy);

                Data data = new Data();

                data.setScore_phrase(ln[3]);
                data.setTitle(ln[1]);
                data.setPlataform(ln[2]);
                data.setScore(Double.parseDouble(ln[4]));
                data.setGenre(ln[5]);
                data.setEditors_choice(ln[6]);
                data.setRelease_year(ln[7]);

                if (!map.containsKey(data.getRelease_year())) {
                    map.put(data.getRelease_year(), new ArrayList<>());
                }
                map.get(data.getRelease_year()).add(data);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        int racing_reviews = 0;
        String racing_year = null;

        for (String s : map.keySet()) {
            System.out.println("Year: " + s);
            System.out.println("Reviews: " + map.get(s).size());

            int masterpiece = 0, great = 0;
            int racing_count = 0;
            double avg, sum = 0.0, variance, deviation;
            double best_score = 0.0, worst_score = 10.0;
            String best_title = null, worst_title = null;
            ArrayList<Double> scores = new ArrayList<>();

            for (int i = 0; i < map.get(s).size(); i++) {
                if (map.get(s).get(i).getScore_phrase().equalsIgnoreCase("Great")) {
                    great += 1;
                }

                if (map.get(s).get(i).getScore_phrase().equalsIgnoreCase("Masterpiece")) {
                    masterpiece += 1;
                }
                if (map.get(s).get(i).getGenre().equalsIgnoreCase("Racing")) {
                    racing_count += 1;
                }

                sum += map.get(s).get(i).getScore();
                scores.add(map.get(s).get(i).getScore());

                if (map.get(s).get(i).getScore() > best_score) {
                    best_score = map.get(s).get(i).getScore();
                    best_title = map.get(s).get(i).getTitle();

                }
                if (map.get(s).get(i).getScore() < worst_score) {
                    worst_score = map.get(s).get(i).getScore();
                    worst_title = map.get(s).get(i).getTitle();
                }

            }

            if (racing_count > racing_reviews) {
                racing_year = s;
                racing_reviews = racing_count;
            }
            avg = sum / map.get(s).size();
            sum = 0.0;
            for (Double score : scores) {
                sum += (score - avg) * (score - avg);
            }
            variance = sum / (map.get(s).size());
            deviation = Math.sqrt(variance);

            System.out.println("Masterpiece reviews: " + masterpiece);
            System.out.println("Great reviews: " + great);
            System.out.println("All scores avarege: " + avg);
            System.out.println("Deviation: " + deviation);
            System.out.println("One of the bests games title: " + best_title + " with score " + best_score);
            System.out.println("One of the worts games title: " + worst_title + " with score " + worst_score);
            System.out.println("");

        }
        System.out.println("");

    }
}
