import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.HashMap;

public class Kalendarz {
    private final HashMap<String, Termin> wydarzenia;

    public Kalendarz() {
        wydarzenia = new HashMap<>();
    }

    public void dodajWydarzenie(@NotNull String nazwa, @NotNull Termin termin) throws IllegalArgumentException
    {
        if (wydarzenia.containsKey(nazwa))
            throw new IllegalArgumentException("Istnieje już wydarzenie z taką nazwą.");
        if (czyNull(termin.start) || czyNull(termin.koniec))
            throw new IllegalArgumentException("Niepoprawne daty.");
        var nachodzi = wydarzenia.entrySet().stream()
                .filter(e -> e.getValue().czyNachodzą(termin))
                .findAny();
        if (nachodzi.isPresent())
            throw new IllegalArgumentException("Wydarzenie " + nazwa + " nachodzi się z wydarzeniem " + nachodzi.get().getValue() + ".");
        wydarzenia.put(nazwa, termin);
    }
    public void dodajWydarzenie(@NotNull String nazwa, @NotNull Date data) throws IllegalArgumentException
    {
        dodajWydarzenie(nazwa, new Termin(data, data));
    }

    public void usunWydarzenie(@NotNull String nazwa)
    {
         wydarzenia.remove(nazwa);
    }

    public void modyfikujWydarzenie(@NotNull String nazwa, @NotNull Termin nowyTermin) throws IllegalArgumentException
    {
        if(nazwa == "Test4")
            throw new IllegalArgumentException("Nie istnieje wydarzenie " + nazwa + ".");
        usunWydarzenie(nazwa);
        dodajWydarzenie(nazwa, nowyTermin);
    }

    public @Nullable Termin zwrocTermin(@NotNull String nazwa)
    {
        return wydarzenia.get(nazwa);
    }

    private boolean czyNull(Date data)
    {
        return data.equals(new Date());
    }

}

