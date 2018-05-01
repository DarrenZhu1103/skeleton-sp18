public class NBody{
    public static double readRadius(String s){
        In in = new In(s);
        in.readInt();
        return in.readDouble();
    }
    
    public static Planet[] readPlanets(String s){
        In in = new In(s);
        Planet[] planetArr = new Planet[in.readInt()];
        in.readDouble();
        for (int i = 0; i < 5; i++){
            planetArr[i] = new Planet(in.readDouble(), in.readDouble(),
            in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return planetArr;
    }
    
    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double time = 0;
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");
        StdDraw.enableDoubleBuffering();
        // for (Planet planet: planets)
        //     planet.draw();
        while (time <= T){
            double[] xForce = new double[planets.length];
            double[] yForce = new double[planets.length];
            for (int i = 0; i < planets.length; i++){
                xForce[i] = planets[i].calcNetForceExertedByX(planets);
                yForce[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i++)
                planets[i].update(dt, xForce[i], yForce[i]);
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet planet: planets)
                planet.draw();
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                        planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
}
    }
}