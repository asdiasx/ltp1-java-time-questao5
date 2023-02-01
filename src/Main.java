
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int NUM_DOSES = 3;
        int INTERVAL_IN_MONTHS = 3;

        LocalDate date = getDateFromTerminal();

        System.out.println("1a dose realizada em: " +
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        System.out.println("Próximas doses:");
        for (int i = 1; i <= NUM_DOSES; i++) {

            var nextDate = date.plusMonths((long) i * INTERVAL_IN_MONTHS);

            var dayOfWeek = nextDate.getDayOfWeek();

            if (dayOfWeek.getValue() == 6)
                nextDate = nextDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
            if (dayOfWeek.getValue() == 7)
                nextDate = nextDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

            System.out.printf("%da dose: Será em %s (%s)%n", i + 1,
                    nextDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    nextDate.getDayOfWeek());
        }
    }

    public static LocalDate getDateFromTerminal() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Entre com a data da vacinação no formato dd/MM/yyyy:");
        String inputDateStr = sc.nextLine();
        while (true) {
            try {
                DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return LocalDate.parse(inputDateStr, myFormatter);
            } catch (Exception ex) {
                System.out.println("""
                        A data informada não é inválida!
                        Tente novamente ou digite <sair>
                        """);
                inputDateStr = sc.nextLine();
                if (inputDateStr.equals("sair")) {
                    sc.close();
                    System.exit(0);
                }
            }
        }
    }
}