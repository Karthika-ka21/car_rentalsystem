import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carid;
    private String brand;
    private String model;
    private double priceperday;
    private boolean isAvailable;
    public Car(String carid,String brand,String model,double priceperday){
        this.carid=carid;
        this.brand=brand;
        this.model=model;
        this.priceperday=priceperday;
        this.isAvailable=true;
    }

    public String getCarid(){
        return carid;
    }
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public double calculateamount(int rentaldays){
        return priceperday*rentaldays;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable=false;
    }
    public void carReturn(){
        isAvailable=true;
    }
}
class Client{
    private String name;
    private String cid;
    private String pnumber;
    private String licence_no;

    public Client(String name,String cid,String pnumber,String licence_no){
        this.name=name;
        this.cid=cid;
        this.pnumber=pnumber;
        this.licence_no=licence_no;

    }
    public String getName(){
        return name;
    }
    public String getCid(){
        return cid;
    }
    public String getPnumber(){
        return pnumber;
    }
    public String getLicenceNo(){
        return licence_no;
    }
}
class Rental{
    private Car car;
    private Client client;
    private int days;

    public Rental(Car car,Client client,int days){
        this.car=car;
        this.client=client;
        this.days=days;
    }
    public Car getCar(){
        return car;
    }
    public Client getClient(){
        return client;
    }
    public int getDays(){
        return days;
    }
}
class CarRentalSystem{
    private List<Car> cars;
    private List<Client> clients;
    private List<Rental> rentals;
    
    public CarRentalSystem(){
        cars=new ArrayList<>();
        clients=new ArrayList<>();
        rentals=new ArrayList<>();
    }
   public void addCar(Car car){
    cars.add(car);
   }  
   public void addClients(Client client){
    clients.add(client); 
   }  
   public void carRent(Car car,Client client,int days){
    if(car.isAvailable()){
        car.rent();
        rentals.add(new Rental(car, client, days));
    }else{
        System.out.println("car is not available");
    }
   }
   public void carReturn(Car car){
      car.carReturn();
      Rental rentalToremove=null;
      for(Rental rental:rentals){
        if(rental.getCar()==car){
            rentalToremove=rental;
            break;
        }
        
      }  
      if(rentalToremove!=null){
        rentals.remove(rentalToremove);
        
      }else{
        System.out.println("car was not rented");
      }
   }
   public void menu(){
    @SuppressWarnings("resource")
    Scanner sc=new Scanner(System.in);
    while(true){
        System.out.println("====car rental system====");
        System.out.println("1.Rent a Car");
        System.out.println("2.Return a car");
        System.out.println("3.Exit");
        System.out.println("Enter your choice");

        int choice=sc.nextInt();
        sc.nextLine();

        if(choice==1){
            System.out.println("==Rent a Car==");
            System.out.println("enter your name: ");
            String cname=sc.nextLine();

            System.out.println("enter your number: ");
            String pno=sc.nextLine();

            System.out.println("enter your licence_no: ");
            String lno=sc.nextLine();

            System.out.println("Available cars:");
            for(Car car:cars){
                if(car.isAvailable()){
                    System.out.println(car.getCarid()+"-"+car.getBrand()+" "+car.getModel());
                }
            }

            System.out.println("Enter the id of car you want to rent");
            String cid=sc.nextLine();

            System.out.println("Enter the number of days to rent");
            int days=sc.nextInt();
            sc.nextLine();

            Client newclient= new Client(cname,"cus"+(clients.size()+1),pno,lno);
            addClients(newclient);

            Car selectedcar=null;
            for(Car car:cars){
                if(car.getCarid().equals(cid)&&car.isAvailable()){
                    selectedcar=car;
                    break;
                }
            }
            if(selectedcar!=null){
                double totalamount=selectedcar.calculateamount(days);
                System.out.println("\n==Rental information==\n");
                System.out.println("Customer id:"+newclient.getCid());
                System.out.println("Customer name:"+newclient.getName());
                System.out.println("Customer no:"+newclient.getPnumber());
                System.out.println("Customer licence_no:"+newclient.getLicenceNo());
                System.out.println("Car:"+selectedcar.getBrand()+selectedcar.getModel());
                System.out.println("Rental days"+days);
                System.out.printf("Total amount:$%.2f%n",totalamount);
            
                System.out.print("\nConfirm rental(Y/N):");
                String confirm = sc.nextLine();

                if(!(confirm.equalsIgnoreCase("Y"))){
                    System.out.println("\nRental cancelled");
                }else{
                    carRent(selectedcar, newclient, days);
                    System.out.println("\nCar rented successfully");
                    
                }

                
            }else{
                System.out.println("\nInvalid car selection or selected car is not available");
            }
        }else if(choice==2){
            System.out.println("enter the id of car you want to return");
            String carno=sc.nextLine();

            Car carToreturn=null;
            for(Car car:cars){
                if(car.getCarid().equals(carno)&&!car.isAvailable()){
                    carToreturn=car;
                    break;
                }
            }

            if(carToreturn!=null){
                Client client=null;
                for(Rental rental:rentals){
                    if(rental.getCar()==carToreturn){
                    client=rental.getClient();
                    break;
                    }
                }
                if(client!=null){
                    carReturn(carToreturn);
                    System.out.println("car returned successfully by "+client.getName());
                }else{
                    System.out.println("Car was not rented or rental information is missing");
                }
           
            }else{
                System.out.println("Invalid car ID or car is not rented");
            }
        }else if(choice==3){
            break;
        }else{
            System.out.println("Invalid choice");
        }
    }
    System.out.println("Thankyou for using car rental system");
   }
}
public class Main {

    public static void main(String[] args) {
        CarRentalSystem rentalsystem= new CarRentalSystem();

        Car car1=new Car("C01", "ford","Aspire" , 1000);
        Car car2=new Car("C02", "Scoda","kodiaq" , 1500);
        Car car3=new Car("C03", "volkswagon","beetle" , 1200);
        Car car4=new Car("C04", "Innova","crista" , 900);
        Car car5=new Car("C05", "Maruthi","suzuki" , 800);

        rentalsystem.addCar(car1);
        rentalsystem.addCar(car2);
        rentalsystem.addCar(car3);
        rentalsystem.addCar(car4);
        rentalsystem.addCar(car5);

        rentalsystem.menu();

    }
}