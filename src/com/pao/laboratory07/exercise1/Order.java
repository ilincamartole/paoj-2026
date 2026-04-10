package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.CannotRevertInitialStareComandaException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

public class Order {

    private StareComanda stare;


    public Order(StareComanda stare){

        this.stare=stare;


    }

    public void nextState() throws OrderIsAlreadyFinalException {
        if (this.stare==StareComanda.PLACED)
            this.stare= StareComanda.PROCESSED;
        else{
            if (this.stare==StareComanda.PROCESSED)
                this.stare=StareComanda.SHIPPED;
            else{
                if (this.stare==StareComanda.SHIPPED)
                    this.stare= StareComanda.DELIVERED;
                else{
                    throw new OrderIsAlreadyFinalException() ;
                }

            }
        }
        System.out.println("Order state updated to: "+this.stare);
    }

    public void cancel() {

        if (this.stare==StareComanda.CANCELED || this.stare==StareComanda.DELIVERED)
            throw new CannotCancelFinalOrderException();



        this.stare=StareComanda.CANCELED;
        System.out.println(

                "Order has been canceled."
        );
    }

    public void undoState() {
        if (this.stare==StareComanda.PROCESSED)
            this.stare= StareComanda.PLACED;
        else{
            if (this.stare==StareComanda.SHIPPED || this.stare==StareComanda.CANCELED )
                this.stare= StareComanda.PROCESSED;
            else{
                if (this.stare==StareComanda.DELIVERED)
                    this.stare= StareComanda.SHIPPED;
                else{
                    throw new CannotRevertInitialStareComandaException() ;
                }

            }
        }
        System.out.println("Order state reverted to: "+this.stare);


    }
    }

