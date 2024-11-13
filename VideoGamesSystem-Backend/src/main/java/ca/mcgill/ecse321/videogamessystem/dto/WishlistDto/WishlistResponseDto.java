package ca.mcgill.ecse321.videogamessystem.dto.WishlistDto;
import ca.mcgill.ecse321.videogamessystem.dto.CustomerDto.CustomerResponseDto;
import ca.mcgill.ecse321.videogamessystem.dto.GameDto.GameListDto;
import ca.mcgill.ecse321.videogamessystem.model.Customer;
import ca.mcgill.ecse321.videogamessystem.model.Wishlist;

public class WishlistResponseDto {
    private Long id;
    private int nbOfItems;

    private Customer customer;
 //   private GameListDto games;

    protected WishlistResponseDto(){} 

    // Constructor that initializes fields directly from a Wishlist entity
    public WishlistResponseDto(Wishlist wishlist) {
        this.id = wishlist.getId();
        this.nbOfItems = wishlist.getNbOfItems();
        
        this.customer = wishlist.getCustomer();
//        this.games= GameListDto.convertToGameListDto(wishlist.getGame());
    }

    // Getter and setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNbOfItems() {
        return nbOfItems;
    }

    public void setNbOfItems(int nbOfItems) {
        this.nbOfItems = nbOfItems;
    }

    public Customer getCustomerResponseDto(){
        return this.customer;
    }
}