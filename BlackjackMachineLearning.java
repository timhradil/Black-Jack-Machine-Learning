\*
* A reinforcement machine learning algorithm to teach a computer to play Black Jack
*
* @author Tim Hradil
*\
public class BlackjackMachineLearning
{
   private static int[] playerHand={0,0,0,0,0,0,0,0,0};
   private static int playerCards= 2;
   private static int[] dealerHand={0,0,0,0,0,0,0,0,0,0,0,0,0};
   private static int dealerCards= 2;
   private static int[] movesAt={0,0,0,0,0,0,0,0,0,0,0};
   private static int[] hittingAt={4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
   private static double[] chanceOfHitting={0,0,0,0,.5,.5,.5,.5,.5,.5,.5,.5,.5,.5,.5,.5,.5,.5,.5,.5,.5};
   private static boolean[] typeOfMove={false,false,false,false,false,false,false,false,false,false};
   private static String[] deck = {"ace","ace","ace","ace","2","2","2","2","3","3","3","3","4","4","4","4","5","5","5","5","6","6","6","6","7","7","7","7","8","8","8","8","9","9","9","9","10","10","10","10","10","10","10","10","10","10","10","10","10","10","10","10"};
   private static int wins=0;
   public static void main(String args[])
   {
         double firstWinPercentage=0.0;
         int round=0;
         double winPercentage=0.0;
         for(int i=0;i<1000000;i++)
         {
            deal();
            movesAt[playerCards]=addUpPlayer();
            while(addUpPlayer()<21&&hitOrNot()==true)
            {
               hitPlayer();
               movesAt[playerCards]=addUpPlayer();
            }
            typeOfMove[playerCards]=false;
            if(addUpPlayer()==21)
            {
               update(true);
               wins++;
            }
            else if(addUpPlayer()>21)
            {
               update(false);
            }
            else
            {
               while(addUpPlayer()>addUpDealer())
               {
                  hitDealer();
               }
               if(addUpDealer()==21)
               {
                  update(false);
               }
               else if(addUpDealer()>21)
                  {
                  update(true);
                  wins++;
               }
               else
                  {
                  update(false);
               }
            }
            if(i%10000==0&&i!=0)
               {
               if(round==0)
                  firstWinPercentage=wins/10000.0;
               winPercentage=wins/10000.0;
               //System.out.println(winPercentage+" "+round);
               wins=0;
               round++;
            }
         }
         for(int i=4;i<chanceOfHitting.length;i++)
         System.out.print(Math.round(chanceOfHitting[i]*100)+" ");
         System.out.println();
         for(int i=0;i<hittingAt.length;i++)
         {
            if(hittingAt[i]<10)
               System.out.print(hittingAt[i]+"  ");
            if(hittingAt[i]>=10)
               System.out.print(hittingAt[i]+" ");
         }
         System.out.println();
         System.out.println(Math.round(winPercentage*100)+" "+winPercentage+" "+firstWinPercentage);
         System.out.println(((winPercentage)/firstWinPercentage)*100.0); 
   }
   public static void hitPlayer()
   {
      int randomIndex=playerHand[0];
      while(works(randomIndex)==false)
      {
         randomIndex=(int)(Math.random()*52);
         if(works(randomIndex))
         {
            playerHand[playerCards]=randomIndex;
         }
      }
      typeOfMove[playerCards]=true;
      playerCards++;
   } 
   public static void hitDealer()
   {
      int randomIndex=dealerHand[0];
      dealerCards++;
      while(works(randomIndex)==false)
      {
         randomIndex=(int)(Math.random()*52);
         if(works(randomIndex))
         {
            dealerHand[dealerCards]=randomIndex;
         }
      }   
   } 
   public static void deal()
   {
      dealerCards=2;
      playerCards=2;
      for(int i=0;i<playerHand.length;i++)
         playerHand[i]=0;
      for(int i=0;i<movesAt.length;i++)
         movesAt[i]=0;
      for(int i=0;i<dealerHand.length;i++)
         dealerHand[i]=0;
      for(int i=0;i<typeOfMove.length;i++)
         typeOfMove[i]=false;
      int randomIndex=(int)(Math.random()*52);
      playerHand[0]=randomIndex;
      while(randomIndex==playerHand[0])
      {
         randomIndex=(int)(Math.random()*52);
         playerHand[1]=randomIndex;
      }
      while(randomIndex==playerHand[0]||randomIndex==playerHand[1])
      {
         randomIndex=(int)(Math.random()*52);
         dealerHand[0]=randomIndex;
      }
      while(randomIndex==playerHand[0]||randomIndex==playerHand[1]||randomIndex==dealerHand[0])
      {
         randomIndex=(int)(Math.random()*52);
         dealerHand[1]=randomIndex;
      }
   }
   public static boolean works(int index)
   {
      for(int i=0;i<playerCards;i++)
      {
         if(playerHand[i]==index)
            return false;
      }
      for(int i=0;i<dealerCards;i++)
      {
         if(dealerHand[i]==index)
            return false;
      }
      return true;
   }
   public static int addUpPlayer()
   {
      int total=0;
      int aceCount=0;
      for(int i=0;i<playerCards;i++)
      {
         if(deck[playerHand[i]].equals("ace"))
         {
            aceCount++;
         }
         else
         {
            total=total+Integer.parseInt(deck[playerHand[i]]);
         }
      }
      if(aceCount==4)
         if(total<=7)
         {
            total+=14;
         }
         else
         {
            total+=4;
         }
      if(aceCount==3)
         if(total<=8)
         {
            total+=13;
         }
         else
         {
            total+=3;
         }
      if(aceCount==2)
         if(total<=9)
         {
            total+=12;
         }
         else
         {
            total+=2;
         }
      if(aceCount==1)
         if(total<=10)
         {
            total+=11;
         }
         else
         {
            total++;
         }
      return total;
   }
   public static int addUpDealer()
   {
      int total=0;
      int aceCount=0;
      for(int i=0;i<dealerCards;i++)
      {
         if(deck[dealerHand[i]].equals("ace"))
         {
            aceCount++;
         }
         else
         {
            total=total+Integer.parseInt(deck[dealerHand[i]]);
         }
      }
      if(aceCount==4)
         if(total<=7)
         {
            total+=14;
         }
         else
         {
            total+=4;
         }
      if(aceCount==3)
         if(total<=8)
         {
            total+=13;
         }
         else
         {
            total+=3;
         }
      if(aceCount==2)
         if(total<=9)
         {
            total+=12;
         }
         else
         {
            total+=2;
         }
      if(aceCount==1)
         if(total<=10)
         {
            total+=11;
         }
         else
         {
            total++;
         }
      return total;
   }
   public static boolean hitOrNot()
   {
      if(Math.random()<chanceOfHitting[movesAt[playerCards]])
      {
         return true;
      }
      return false;
   }
   public static void update(boolean success)
   {
      if(success)
      {
         for(int i=2;i<playerCards;i++)
         {
               if(typeOfMove[i]=true)
                  chanceOfHitting[movesAt[i]]=chanceOfHitting[movesAt[i]]+.0001;
               if(typeOfMove[i]=false)
                  chanceOfHitting[movesAt[i]]=chanceOfHitting[movesAt[i]]-.0001;
         }
      }
      else
      {
         for(int i=2;i<playerCards;i++)
         {
               if(typeOfMove[i]=true)
                  chanceOfHitting[movesAt[i]]=chanceOfHitting[movesAt[i]]-.00005;
               if(typeOfMove[i]=false)
                  chanceOfHitting[movesAt[i]]=chanceOfHitting[movesAt[i]]+.00005;
         }
      }
   }
}
