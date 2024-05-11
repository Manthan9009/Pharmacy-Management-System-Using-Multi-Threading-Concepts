import java.util.*;

class Medicine{
    String name;
    double price;
    int quantity;
    
    public Medicine(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Medicine [name=" + name + ", price=" + price + ", quantity=" + quantity + "]";
    }
}

class Pharmacy{
    LinkedList<Medicine> medicines;

    public Pharmacy(){
        this.medicines = new LinkedList<>();
    }

    public synchronized void addMedicine(Medicine medicine){
        medicines.add(medicine);
    }

    public synchronized void removeMedicine(String name){
        boolean flag=false;
        for(Medicine s : medicines){
            if(s.name.equalsIgnoreCase(name)){
                medicines.remove(s);
                System.out.println("Medicine Deleted Successfully!!");
                flag=true;
                break;
            }
        }
        if(flag==false){
            System.out.println("Medicine Of Given name is not found");
        }
    } 

    public synchronized void updateMedicine(String newName){
        boolean flag=false;
        for(Medicine s : medicines){
            if(s.name.equalsIgnoreCase(newName)){
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter new Price : ");
                double newPrice = sc.nextDouble();
                s.price=newPrice;

                System.out.println("Enter new Quantity : ");
                int newQuantity = sc.nextInt();
                s.quantity=newQuantity;

                System.out.println("Medicine's price and Quantity updated successfully");
                
                flag=true;
                break;
            }
        }
        if(flag==false){
            System.out.println("Medicine Of Given name is not found");
        }
    }

    public synchronized void displayByName(){
        if(medicines.isEmpty()){
            System.out.println("No Any Medicines Found");
        }

        else{
            System.out.println();
            System.out.println("Medicines Sorted by names");
            System.out.println("Medicines Inventory : ");

            Collections.sort(medicines, Comparator.comparing(Medicine :: getName));

            Iterator itr1 = medicines.iterator();
            while(itr1.hasNext()){
                System.out.println(itr1.next());
            }
        }
    }

    public synchronized void displayByHighestPrice(){
        if(medicines.isEmpty()){
            System.out.println("No Any Medicines Found");
        }

        else{
            System.out.println();
            System.out.println("Medicines Sorted by Highest Price");
            System.out.println("Medicines Inventory : ");

            Collections.sort(medicines, Comparator.comparing(Medicine :: getPrice).reversed());

            Iterator itr1 = medicines.iterator();
            while(itr1.hasNext()){
                System.out.println(itr1.next());
            }
        }
    }

    
    public synchronized void displayByHighestQuantity(){
        if(medicines.isEmpty()){
            System.out.println("No Any Medicines Found");
        }

        else{
            System.out.println();
            System.out.println("Medicines Sorted by Highest Quantity");
            System.out.println("Medicines Inventory : ");

            Collections.sort(medicines, Comparator.comparing(Medicine :: getQuantity).reversed());

            Iterator itr1 = medicines.iterator();
            while(itr1.hasNext()){
                System.out.println(itr1.next());
            }
        }
    }

    public void sellMedicine(String sellName, int sellQuantity){
        if(medicines.isEmpty()){
            System.out.println("No Any Medicines Found");
        }

        else{
            for(Medicine s : medicines){
                if(s.name.equalsIgnoreCase(sellName)){
                    if(s.quantity >= sellQuantity){
                        s.setQuantity(s.getQuantity()-sellQuantity);
                        System.out.println("Medicine Successfully sold to user : " + Thread.currentThread().getName());
                        return;
                    }
                    else{
                        System.out.println("Insufficient Stock for user : " + Thread.currentThread().getName());
                        return;
                    }
                }
            }
            System.out.println("Medicine not found");
        }
    }
    
}

class PMSystem extends Thread{
    static Pharmacy pharmacy;

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        pharmacy = new Pharmacy();

        // Adding sample medicines
        pharmacy.addMedicine(new Medicine("Paracetamol", 50.0, 20));
        pharmacy.addMedicine(new Medicine("Ibuprofen", 80.0, 15));
        pharmacy.addMedicine(new Medicine("Saridone", 120.0, 100));
        pharmacy.addMedicine(new Medicine("Dolo", 150.0, 30));
        pharmacy.addMedicine(new Medicine("Disprine", 200.0, 10));

        int choice =0;

        ArrayList<Thread> userThread = new ArrayList<>();

        while(choice!=6){
            System.out.println("Pharmacy Management System");
            System.out.println("1. Display Medicines");
            System.out.println("2. Sell Medicine");
            System.out.println("3. Add Medicine");
            System.out.println("4. Remove Medicine");
            System.out.println("5. Update Price by Medicine name");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch(choice){
                case 1: 
                        System.out.println("\t Enter Display choice: ");
                        System.out.println("\t 1. Display Medicines By Sorting Name");
                        System.out.println("\t 2. Display Medicines By Sorting Highest Price");
                        System.out.println("\t 3. Display Medicines By Sorting Highest Quantity");
                        int c = sc.nextInt();

                        switch(c){
                            case 1:
                                    pharmacy.displayByName();
                                    break;
                            
                            case 2:
                                    pharmacy.displayByHighestPrice();
                                    break;
                            
                            case 3:
                                    pharmacy.displayByHighestQuantity();
                                    break;
                            
                            default : System.out.println("Invalid Display choice");
                            
                        }
                        break;
                
                case 2: //SONU Thread
                        PMSystem sonuThread = new PMSystem();
                        sonuThread.setName("Sonu");
                        sonuThread.start();
                        try {
                            sonuThread.join();
                        } catch (InterruptedException e) {}

                        //MONU Thread
                        PMSystem monuThread = new PMSystem();
                        monuThread.setName("Monu");
                        monuThread.start();
                        try {
                            monuThread.join();
                        } catch (InterruptedException e) {}


                        break;
                
                case 3: System.out.print("Enter the name of the medicine: ");
                        String newMedicineName = sc.next();
                        System.out.print("Enter the price of the medicine: ");
                        double newMedicinePrice = sc.nextDouble();
                        System.out.print("Enter the quantity of the medicine: ");
                        int newMedicineQuantity = sc.nextInt();
                        Medicine newMedicine = new Medicine(newMedicineName, newMedicinePrice, newMedicineQuantity);
                        pharmacy.addMedicine(newMedicine);
                        System.out.println("Medicine Added Successfully");

                        break;
                
                case 4: 
                        System.out.println("Enter Name of medicine to remove : ");
                        String remName = sc.next();

                        pharmacy.removeMedicine(remName);
                        break;
                
                case 5: 
                        System.out.println("Enter Name of Medicine : ");
                        String upName = sc.next();
                        
                        pharmacy.updateMedicine(upName);
                        break;
                
                case 6: 
                        System.out.println("Exiting from System.");
                        System.exit(0);
                        
                default : System.out.println("Invalid Choice");
            }
        }
    }

    public void run(){
        Scanner sc = new Scanner (System.in);
        synchronized(this){
            System.out.println("Hi " + getName());
            System.out.println("Aailabke Medicines List : ");
            Iterator itr1 = pharmacy.medicines.iterator();
            while(itr1.hasNext()){
                System.out.println(itr1.next());
            }

            System.out.println();
            System.out.println("Enter Name Of Medicine : ");
            String newMedicine  = sc.next();
            
            System.out.println("Enter Quantity : ");
            int sellQuantity  = sc.nextInt();
            
            pharmacy.sellMedicine(newMedicine, sellQuantity);
        }
    }
}