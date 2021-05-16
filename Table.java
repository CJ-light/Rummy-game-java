import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import java.io.*;
/**
*	This GUI assumes that you are using a 52 card deck and that you have 13 sets in the deck.
*	The GUI is simulating a playing table
	@author Patti Ordonez
*/
public class Table extends JFrame implements ActionListener
{
	final static int numDealtCards = 9;
	JPanel player1;
	JPanel player2;
	JPanel deckPiles;
	JLabel deck;
	JLabel decknum;
	JLabel stack;
	int numStack;
	JLabel stackNum;
	JList p1HandPile;
	JList p2HandPile;
	Deck cardDeck;
	Stack stackDeck;
	//Deck stackDeck;

	SetPanel [] setPanels = new SetPanel[13];
	JLabel topOfStack;
	JLabel deckPile;
	JButton p1Stack;
	JButton p2Stack;

	JButton p1Deck;
	JButton p2Deck;

	JButton p1Lay;
	JButton p2Lay;

	JButton p1LayOnStack;
	JButton p2LayOnStack;

	JLabel p1Instructions;
	JLabel p2Instructions;

	Hand p1Hand;
	DefaultListModel p1ListHand;
	Hand p2Hand;
	DefaultListModel p2ListHand;

	Hand tempHand;

	Hand addedHand = new Hand();
	Hand discardedHand = new Hand();

	//these will determine each turn
	//Player will determine which player can go
	//added will change to true after the player gets a card from the Stack or Deck pile
	//Then they can lay how many cards they want (can only do this when added == true and discarded == false)
	//discared will change once the player puts a card to the Stack pile (can only do this once added == true)
	//After discarded has changed to 1 then change to the other player to start their turn (assuming none of them have won) and reset both added and discard variables to false  
	int player = 0;
	boolean added = false;
	boolean discarded = false;
	boolean gameEnd = false;


	private void deal(Card [] cards)
	{
		for(int i = 0; i < cards.length; i ++)
			cards[i] = (Card)cardDeck.dealCard();
	}

	public Table()
	{
		super("The Card Game of the Century");

		setLayout(new BorderLayout());
		setSize(1200,700);


		cardDeck = new Deck();

		stackDeck = new Stack(); //make new stack deck


		JPanel top = new JPanel();

		for (int i = 0; i < Card.rank.length;i++)
			setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));


		top.add(setPanels[0]);
		top.add(setPanels[1]);
		top.add(setPanels[2]);
		top.add(setPanels[3]);

		player1 = new JPanel();

		player1.add(top);




		add(player1, BorderLayout.NORTH);
		JPanel bottom = new JPanel();


		bottom.add(setPanels[4]);
		bottom.add(setPanels[5]);
		bottom.add(setPanels[6]);
		bottom.add(setPanels[7]);
		bottom.add(setPanels[8]);

		player2 = new JPanel();




		player2.add(bottom);
		add(player2, BorderLayout.SOUTH);


		JPanel middle = new JPanel(new GridLayout(1,3));

		p1Stack = new JButton("Stack");
		p1Stack.addActionListener(this);
		p1Deck = new JButton("Deck ");
		p1Deck.addActionListener(this);
		p1Lay = new JButton("Lay  ");
		p1Lay.addActionListener(this);
		p1LayOnStack = new JButton("LayOnStack");
		p1LayOnStack.addActionListener(this);
		p1Instructions = new JLabel("P1's turn: Get a card from the Stack or Deck");


		Card [] cardsPlayer1 = new Card[numDealtCards];
		deal(cardsPlayer1);
		p1Hand = new Hand();
		for(int i = 0; i < cardsPlayer1.length; i++)
			p1Hand.addCard(cardsPlayer1[i]);
		p1Hand.sort();
		p1ListHand = new DefaultListModel();
		for(int i = 0; i < p1Hand.getNumberOfCards(); i++)
			p1ListHand.addElement(p1Hand.getCard(i));

		p1HandPile = new JList(p1ListHand);


		//Card [] cardsPlayer1 = new Card[numDealtCards];
		//deal(cardsPlayer1);
		//p1Hand = new DefaultListModel();
		//for(int i = 0; i < cardsPlayer1.length; i++)
		//	p1Hand.addElement(cardsPlayer1[i]);
		//p1HandPile = new JList(p1Hand);


		middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack,p1Instructions));

		deckPiles = new JPanel();
		deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
		deckPiles.add(Box.createGlue());
		JPanel left = new JPanel();
		left.setAlignmentY(Component.CENTER_ALIGNMENT);


		stack = new JLabel("Stack");

		stackNum = new JLabel(Integer.toString(stackDeck.getSizeOfStack()));

		stack.setAlignmentY(Component.CENTER_ALIGNMENT);

		left.add(stack);
		topOfStack = new JLabel();
		topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
		topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
		left.add(topOfStack);
		left.add(stackNum);
		deckPiles.add(left);
		deckPiles.add(Box.createGlue());

		JPanel right = new JPanel();
		right.setAlignmentY(Component.CENTER_ALIGNMENT);

		deck = new JLabel("Deck");

		decknum = new JLabel(Integer.toString(cardDeck.getSizeOfDeck()));

		deck.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deck);
		deckPile = new JLabel();
		deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
		deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deckPile);
		right.add(decknum);
		deckPiles.add(right);
		deckPiles.add(Box.createGlue());
		middle.add(deckPiles);


		p2Stack = new JButton("Stack");
		p2Stack.addActionListener(this);
		p2Deck = new JButton("Deck ");
		p2Deck.addActionListener(this);
		p2Lay = new JButton("Lay  ");
		p2Lay.addActionListener(this);
		p2LayOnStack = new JButton("LayOnStack");
		p2LayOnStack.addActionListener(this);
		p2Instructions = new JLabel("Not your turn");

		Card [] cardsPlayer2 = new Card[numDealtCards];
		deal(cardsPlayer2);
		p2Hand = new Hand();
		for(int i = 0; i < cardsPlayer2.length; i++)
			p2Hand.addCard(cardsPlayer2[i]);
		p2Hand.sort();

		p2ListHand = new DefaultListModel();
		for(int i = 0; i < p2Hand.getNumberOfCards(); i++)
			p2ListHand.addElement(p2Hand.getCard(i));

		p2HandPile = new JList(p2ListHand);


		middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack,p2Instructions));

		add(middle, BorderLayout.CENTER);

		JPanel leftBorder = new JPanel(new GridLayout(2,1));


		setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
		setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
		leftBorder.add(setPanels[9]);
		leftBorder.add(setPanels[10]);
		add(leftBorder, BorderLayout.WEST);

		JPanel rightBorder = new JPanel(new GridLayout(2,1));

		setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
		setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
		rightBorder.add(setPanels[11]);
		rightBorder.add(setPanels[12]);
		add(rightBorder, BorderLayout.EAST);

		// At the end put one card from the deck to the discard pile and show the icon of that card
		Card tempCard = cardDeck.removeCard();
		decknum.setText(Integer.toString(cardDeck.getSizeOfDeck()));
		stackDeck.addCard(tempCard);
		stackNum.setText(Integer.toString(stackDeck.getSizeOfStack()));
		topOfStack.setIcon(tempCard.getCardImage());

		System.out.println("initial Player 1: " + p1Hand.toString());
		System.out.println("initial Player 2: " + p2Hand.toString());
		System.out.println("Player 1");
	}

	//checking if they're clikcing the buttons and if they are then do those actions accordingly
	public void actionPerformed(ActionEvent e)
	{
		if (gameEnd == false){
			Object src = e.getSource();
			if((p1Deck == src|| p2Deck == src) && added == false && discarded == false){

				Card deckTop = cardDeck.peek();
				if (deckTop != null){
					if(src == p1Deck && player == 0){
						Card card = cardDeck.dealCard();
						addedHand.addCard(card);
						p1Hand.addCard(card);
						p1Hand.sort();
						p1ListHand.add(p1Hand.findCard(card),card); //adds that card to that index
						added = true;
					}
					else if (src == p2Deck && player == 1){
						Card card = cardDeck.dealCard();
						addedHand.addCard(card);
						p2Hand.addCard(card);
						p2Hand.sort();
						p2ListHand.add(p2Hand.findCard(card),card);
						added = true;
					}
				}
			}

			if((p1Stack == src || p2Stack == src) && added == false && discarded == false){
				
				Card stackTop = stackDeck.peek();
				if(stackTop != null){
					if(p1Stack == src && player == 0){
						Card card = stackDeck.removeCard();
						addedHand.addCard(card);
						p1Hand.addCard(card);
						p1Hand.sort();
						p1ListHand.add(p1Hand.findCard(card),card); 
						added = true;
					}
					else if (p2Stack == src && player == 1){
						Card card = stackDeck.removeCard();
						addedHand.addCard(card);
						p2Hand.addCard(card);
						p2Hand.sort();
						p2ListHand.add(p2Hand.findCard(card),card);
						added = true;
					}
				}
			}

			if(p1Lay == src && player == 0 && added == true && discarded == false){
				Object [] cards = p1HandPile.getSelectedValues(); //Can select multiple values by clicling on each button while holding ctrl
				if (cards != null){
					//temporary hand that has these cards
					tempHand = new Hand();
					for (int i = 0; i < cards.length; i ++){
						tempHand.addCard((Card)cards[i]);
					}
					//Find a set of that hand
					Object[] set = tempHand.findSet();
					//If there was no set, or there was a set but there were aditional cards picked then dont add that set 
					if (set != null && set.length == cards.length){
						for(int i = 0; i < cards.length; i++) //iterates over every selected card.
						{
							Card card = (Card)cards[i];
							layCard(card);
							discardedHand.addCard(card);
							p1Hand.removeCard(card);
							p1ListHand.removeElement(card);
						}
					}	
				}
			}


			if(p2Lay == src && player == 1 && added == true && discarded == false){
				Object [] cards = p2HandPile.getSelectedValues();
				if (cards != null){
					tempHand = new Hand();
					for (int i = 0; i < cards.length; i ++){
						tempHand.addCard((Card)cards[i]);
					}
					Object[] set = tempHand.findSet();
					if (set != null && set.length == cards.length){
						for(int i = 0; i < cards.length; i++) 
						{
							Card card = (Card)cards[i];
							layCard(card);
							discardedHand.addCard(card);
							p2Hand.removeCard(card);
							p2ListHand.removeElement(card);
						}
					}	
				}
			}


			if(p1LayOnStack == src && player == 0 && added == true && discarded == false){
				int [] num  = p1HandPile.getSelectedIndices();
				if (num.length == 1)
				{
					Object obj = p1HandPile.getSelectedValue();
					if (obj != null)
					{
						p1ListHand.removeElement(obj);
						Card card = (Card)obj;
						discardedHand.addCard(card);
						p1Hand.removeCard(card);
						stackDeck.addCard(card);
						discarded = true;
					}
				}
			}


			if(p2LayOnStack == src && player == 1 && added == true && discarded == false){
				int [] num  = p2HandPile.getSelectedIndices();
				if (num.length == 1)
				{
					Object obj = p2HandPile.getSelectedValue();
					if (obj != null)
					{

						p2ListHand.removeElement(obj);
						Card card = (Card)obj;
						discardedHand.addCard(card);
						p2Hand.removeCard(card);
						stackDeck.addCard(card);
						discarded = true;
					}
				}
			}

			redraw();
		}
	}

	void redraw(){
		//This function changes the GUI again, based on what you changed int he controller
		if(cardDeck.getSizeOfDeck() == 0)
			deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));

		Card topCard = stackDeck.peek();
		if (topCard != null)
			topOfStack.setIcon(topCard.getCardImage());
		else
			topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));

		decknum.setText(Integer.toString(cardDeck.getSizeOfDeck()));
		stackNum.setText(Integer.toString(stackDeck.getSizeOfStack()));
		String tempWin = CheckWin(cardDeck.getSizeOfDeck(), p1Hand, p2Hand);
		if (tempWin != null)
			System.out.println(tempWin);
		//change to next player's turn and reset everything for that turn
		else if (discarded == true){ 
			System.out.println("	Added: " + addedHand.toString());
			System.out.println("	Discarded: " + discardedHand.toString());
			addedHand.discardHand();
			discardedHand.discardHand();
			if (player == 0)
				System.out.println("	Hand Now: " + p1Hand.toString());
			else if (player == 1)
				System.out.println("	Hand Now: " + p2Hand.toString());

			player = (player + 1) % 2;
			added = false;
			discarded = false;
			if (player == 0)
				System.out.println("Player 1");
			else if (player == 1)
				System.out.println("Player 2");
		}

		if (player == 0){
			if (gameEnd == true){
				p1Instructions.setText(CheckWin(cardDeck.getSizeOfDeck(),p1Hand,p2Hand));
				p2Instructions.setText(CheckWin(cardDeck.getSizeOfDeck(),p1Hand,p2Hand));
			}
			if (added == false && discarded == false){
				p1Instructions.setText("Player 1's turn: Add a card from the Deck or Stack.");
				p2Instructions.setText("Not your turn");
			}
			if (added == true && discarded == false){
				p1Instructions.setText("Player 1's turn: Lay sets, turn ends on a discard.");
				p2Instructions.setText("Not your turn");
			}
		}
		if (player == 1){
			if (gameEnd == true){
				p1Instructions.setText(CheckWin(cardDeck.getSizeOfDeck(),p1Hand,p2Hand));
				p2Instructions.setText(CheckWin(cardDeck.getSizeOfDeck(),p1Hand,p2Hand));
			}
			if (added == false && discarded == false){
				p1Instructions.setText("Not your turn.");
				p2Instructions.setText("Player 2's turn: Add a card from the Deck or Stack.");
			}
			if (added == true && discarded == false){
				p1Instructions.setText("Not your turn.");
				p2Instructions.setText("Player 2's turn: Lay sets, turn ends on a discard.");
			}
		}
	}

	String CheckWin(int deckSize, Hand p1Hand, Hand p2Hand){
		if (deckSize > 0){
			if (p1Hand.getNumberOfCards() == 0)
				return "Player 1 Wins!";
			else if (p2Hand.getNumberOfCards() == 0)
				return "Player 2 Wins!";
		}
		else {
			int p1Eval = p1Hand.evaluateHand();
			int p2Eval = p2Hand.evaluateHand();
			System.out.println(p1Eval);
			System.out.println(p2Eval);
			if (p1Eval < p2Eval)
				return "Player 1 Wins!";
			else if (p1Eval > p2Eval)
				return "Player 2 Wins!";
			else
				return "It's a tie!";
		}
		return null;
	}

	public static void main(String args[])
	{
		Table t = new Table();
		t.setVisible(true);
	}
	void layCard(Card card)
	{
		char rank = card.getRank();
		char suit = card.getSuit();
		int suitIndex =  Card.getSuitIndex(suit);
		int rankIndex =  Card.getRankIndex(rank);
		//setPanels[rankIndex].array[suitIndex].setText(card.toString());
		//System.out.println("laying " + card);
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());	//Setpanels are 13, and each of them have 4 spaces inside
	}

}

class HandPanel extends JPanel
{

	public HandPanel(String name,JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack, JLabel instructions)
	{
		//model = hand.createSelectionModel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		add(Box.createGlue());
		JLabel label = new JLabel(name);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		stack.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(stack);
		deck.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(deck);
		lay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lay);
		layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(layOnStack);
		instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(instructions);

		add(Box.createGlue());
		add(hand);
		add(Box.createGlue());
	}

}
class SetPanel extends JPanel
{
	private Set data;
	JButton [] array = new JButton[4];

	public SetPanel(int index)
	{
		super();
		data = new Set(Card.rank[index]);

		for(int i = 0; i < array.length; i++){
			array[i] = new JButton("   ");
			add(array[i]);
		}
	}

}
