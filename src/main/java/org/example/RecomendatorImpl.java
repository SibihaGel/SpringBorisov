package org.example;

@Singletone
@Transaction
public class RecomendatorImpl implements Recomendator {
    @InjectProperty("whisky")
    private String alcohol;
    @Override
    public void recomend() {
        System.out.println("Против коронны пейте ПИВО : " + alcohol);
    }

    public RecomendatorImpl() {
        System.out.println("RecomendatorImpl - был создан!");;
    }
}