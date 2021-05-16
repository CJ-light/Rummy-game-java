

import java.util.*;

public class Deck {

   java.util.LinkedList deck;
   private int index;


  /**
   * Creates an empty deck of cards.
   */
   public Deck() {
    deck = new LinkedList();

    for(int i = 0; i < Card.suit.length; i++){
      for(int j = 0; j < Card.rank.length; j++){
        Card card = new Card(Card.suit[i],Card.rank[j]);
        this.addCard(card);
      }
    }

    //shuffle deck
    this.shuffle();
   }
	public Card peek()
	{
		if(deck.size() == 0)
			return null;
		else
			return (Card)deck.getLast();
	}

   public void addCard( Card card ) {
      deck.add( card );
   }


   public int getSizeOfDeck() {
      return deck.size();
   }

   public Card dealCard() {
   
	 if ( deck.size() == 0 )
         return null;
    else
	    return (Card) deck.removeFirst();
   }
   public Card removeCard() {
 
	if (deck.size() == 0)
         return null;
      else{
		
         return (Card) deck.removeLast( );
    }
  }


  /**
   * Shuffles the cards present in the deck.
   */
   public void shuffle() {
      Collections.shuffle( deck );
   }


  /**
   * Looks for an empty deck.
   * @return <code>true</code> if there are no cards left to be dealt from the deck.
   */
   public boolean isEmpty() {
		return deck.size() == 0;
   }



  /**
   * Restores the deck to "full deck" status.
   */
  public void restoreDeck() {
	//not sure if kosher
      deck.removeAll(deck);
   }
   

}
