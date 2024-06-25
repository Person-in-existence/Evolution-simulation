public class Creature {
    public controller control;
    public Float heatLoss;
    public Float heatGain;
    public Float heat;
    public Integer red;
    public Integer green;
    public Integer blue;
    public Float greed;
    public Float stomachSize;
    public Float stomachEfficiency;
    public Float stomachFill;
    public Float baseReproductionChance;
    public Float ferocity;
    public Float health = 5F;
    public String name;
    public int x = Math.abs(Math.round(Main.randomFloat(400)));
    public int y = Math.abs(Math.round(Main.randomFloat(400)));
    double dx = 0;
    double dy = 0;
    public void tick(Float foodAlloc) {
        // foodAlloc must be pre-corrected for greed
        Float passiveHeatGain = control.passiveHeatGain;
        passiveHeatGain *= ((-heat/25)+2);
        heat += (heatGain+passiveHeatGain-heatLoss);
        float stomachEfficiencyFinal = (float) Main.sigmoid(Main.unsigmoid(stomachEfficiency)-(stomachSize-10)/10);
        if (foodAlloc-1 > 0) {
            if (stomachFill + (foodAlloc - 1) * stomachEfficiencyFinal < stomachSize) {
                stomachFill += (foodAlloc - 1) * stomachEfficiencyFinal;
            } else {
                stomachFill = stomachSize;
            }


        } else {
            stomachFill += (foodAlloc-1);
        }
        if (health != 5) {
            if (stomachFill >= (5 - health)) {
                stomachFill -= (5 - health);
                health = 5F;
            } else {
                health += stomachFill;
                stomachFill = 0F;
            }
        }
        if (stomachFill < 0) {
            health += stomachFill;
            stomachFill = 0F;
        }
        if (heat > 50) {
            health -= heat-50;
        } else if (heat < 0) {
            health += heat;
        }
        if (health <= 0) {
            control.kill(this);
        }
        if (health > 5) {
            health = 5F;
        }
        // Move Logic
        if (x < 0 || x > 485) dx *= -1;
        if (y < 0 || y > 400) dy *= -1;
        if (x < 0) {
            x += 10;
        } else if (x > 485) {
            x -= 10;
        }
        if (y < 0) {
            y += 10;
        } else if (y > 400) {
            y -= 10;
        }
        dx += Main.randomFloat(5);
        dy += Main.randomFloat(5);
        x += (int) Math.round(dx);
        y += (int) Math.round(dy);


    }
}
