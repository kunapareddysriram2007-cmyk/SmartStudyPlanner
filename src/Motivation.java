import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// CO-6: collections used here; CO-5: interface implementation
public class Motivation implements Motivatable {
    private final List<String> quotes = new ArrayList<>();
    private final Random rnd = new Random();

    public Motivation() {
        quotes.add("Small steps every day lead to big progress.");
        quotes.add("Focus on progress, not perfection.");
        quotes.add("Practice consistently and results will follow.");
        quotes.add("One hour of study beats one hour of worry.");
        quotes.add("Your future is built by what you do today.");
    }

    @Override
    public String getMotivationalMessage() {
        return quotes.get(rnd.nextInt(quotes.size()));
    }
}

// CO-5: interface for Motivatable (package-private)
interface Motivatable {
    String getMotivationalMessage();
}
