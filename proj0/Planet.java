public class Planet{
    double xxPos;
    double yyPos;
    double xxVel;
    double yyVel;
    double mass;
    String imgFileName;
    static final double G = 6.67e-11;
    
    public Planet(double xP, double yP, double xV, double yV, 
    double m, String img){
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }
    
    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }
    
    public double calcDistance(Planet p){
        double xDis = Math.pow(this.xxPos - p.xxPos, 2);
        double yDis = Math.pow(this.yyPos - p.yyPos, 2);
        return Math.pow(xDis + yDis, 0.5);
    }
    
    /** caculate the force */
    public double calcForceExertedBy(Planet p){
        return G * this.mass * p.mass / Math.pow(this.calcDistance(p), 2);
    }
    
    public double calcForceExertedByX(Planet p){
        return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
    }
    
    public double calcForceExertedByY(Planet p){
        return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
    }
    
    public double calcNetForceExertedByX(Planet[] pArray){
        double netForce = 0;
        for (Planet p: pArray){
            if (this.equals(p))
                continue;
            netForce += this.calcForceExertedByX(p);
        }
        return netForce;
    }
    
    public double calcNetForceExertedByY(Planet[] pArray){
        double netForce = 0;
        for (Planet p: pArray){
            if (this.equals(p))
                continue;
            netForce += this.calcForceExertedByY(p);
        }
        return netForce;
    }
    
    public void update(double dt, double fX, double fY){
        this.xxVel += dt * fX / this.mass;
        this.yyVel += dt * fY / this.mass;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }
    
    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}