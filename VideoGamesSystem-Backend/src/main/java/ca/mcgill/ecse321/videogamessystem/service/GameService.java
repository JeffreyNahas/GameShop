package ca.mcgill.ecse321.videogamessystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import ca.mcgill.ecse321.videogamessystem.model.Game;
import ca.mcgill.ecse321.videogamessystem.model.Game.Category;
import ca.mcgill.ecse321.videogamessystem.model.Game.ConsoleType;
import ca.mcgill.ecse321.videogamessystem.model.Promotion;
import ca.mcgill.ecse321.videogamessystem.model.SpecificGame;
import ca.mcgill.ecse321.videogamessystem.model.SpecificOrder;
import ca.mcgill.ecse321.videogamessystem.model.Wishlist;
import ca.mcgill.ecse321.videogamessystem.repository.WishlistRepository;
import ca.mcgill.ecse321.videogamessystem.repository.GameRepository;
import ca.mcgill.ecse321.videogamessystem.repository.SpecificGameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private SpecificGameRepository specificGameRepository;
    @Autowired
    private WishlistRepository wishlistRepository;

    /**
     * @param description
     * @param stockQuantity
     * @param price
     * @param title
     * @param category
     * @param consoleType
     * @return
     */
    @Transactional
    public Game createGame(String description, int stockQuantity, int price, String title, Category category, ConsoleType consoleType) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }
        if (consoleType == null) {
            throw new IllegalArgumentException("consoleType cannot be null.");
        }

        Game game = new Game(description, price, title, category, consoleType);
        return gameRepository.save(game);
    }

    /**
     * @param id
     * @return
     */
    @Transactional
    public Game getGameById(Long id) {
        Game game = gameRepository.findGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("Game not found.");
        }
        return game;
    }


    /**
     * @param price
     * @return
     */
    @Transactional
    public List<Game> getGamesByPrice(int price) {
        return gameRepository.findGameByprice(price);
    }

    @Transactional
    public List<Game> getGamesByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        return gameRepository.findGameByTitle(title);
    }

    /**
     * @param category
     * @return
     */
    @Transactional
    public List<Game> getGamesByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }
        return gameRepository.findGameByCategory(category);
    }

    /**
     * @param consoleType
     * @return
     */
    @Transactional
    public List<Game> getGamesByConsoleType(ConsoleType consoleType) {
        if (consoleType == null) {
            throw new IllegalArgumentException("consoleType cannot be null.");
        }
        return gameRepository.findGameByConsoleType(consoleType);
    }

    /**
     * @param promotion
     * @return
     */
    @Transactional
    public List<Game> getGamesByPromotion(Promotion promotion) {
        if(promotion == null){
            new IllegalArgumentException("promotion is null");
        }
        return gameRepository.findGameByPromotion(promotion);
    }

    /**
     * @param id
     * @param category
     * @return
     */
    @Transactional
    public Game updateCategory(Long id, Category category) {
        Game game = gameRepository.findGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("invalid id to update category");
        }
        game.setCategory(category);
        return gameRepository.save(game);

    }

    /**
     * @param id
     * @param consoleType
     * @return
     */
    @Transactional
    public Game updateConsoleType(Long id, ConsoleType consoleType) {
        Game game = gameRepository.findGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("invalid id to update consoleType");
        }
        game.setConsoleType(consoleType);
        return gameRepository.save(game);
    }

    /**
     * @param id
     * @param description
     * @param stockQuantity
     * @param price
     * @param title
     * @param category
     * @return
     */
    @Transactional
    public Game updateGame(long id, String description, int stockQuantity, int price, String title, Category category){
        Game game = gameRepository.findGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("invalid id to update description");
        }
        game.setDescription(description);
        game.setPrice(price);
        game.setDescription(title);
        game.setCategory(category);

        return gameRepository.save(game);
    }


    /**
     * @param id
     * @param newPrice
     * @return
     */
    @Transactional
    public Game updatePrice(Long id, int newPrice) {
        Game game = gameRepository.findGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("Game not found.");
        }
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        game.setPrice(newPrice);
        return gameRepository.save(game);
    }

    /**
     * @param id
     * @param newDescription
     * @return
     */
    @Transactional
    public Game updateDescription(Long id, String newDescription) {
        Game game = gameRepository.findGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("Game not found.");
        }
        if (newDescription == null || newDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        game.setDescription(newDescription);
        return gameRepository.save(game);
    }

    /**
     * @param id
     * @param newTitle
     * @return
     */
    @Transactional
    public Game updateTitle(Long id, String newTitle) {
        Game game = gameRepository.findGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("Game not found.");
        }
        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }

        game.setTitle(newTitle);
        return gameRepository.save(game);
    }

    /**
     * @param id
     * @return
     */
    @Transactional
    public Game deleteGame(Long id) {
        Game game = gameRepository.findGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("Game not found.");
        }

        gameRepository.delete(game);
        return game;
    }
    

    /**
     * @return
     */
    @Transactional
    public List<Game> getAllGames() {
        return toList(gameRepository.findAll());
    }

    /**
     * @param gameId
     * @param wishlistId
     * @return
     */
    @Transactional
    public Game addGameToWishlist(Long gameId, Long wishlistId) {
        Game game = gameRepository.findGameById(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found.");
        }

        Wishlist wishlist = wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new IllegalArgumentException("Wishlist not found."));
        
        game.setWishlist(wishlist);
        return gameRepository.save(game);
    }

    /**
     * Converts an {@code Iterable} to a {@code List}.
     * @param iterable the {@code Iterable} to convert
     * @param <T>      the type of elements in the iterable
     * @return a {@code List} containing the elements of the {@code Iterable}
     */
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * @param wishlist
     * @return
     */
    public List<Game> getGameByWishlist(Wishlist wishlist){
        if (wishlist == null){
            throw new IllegalArgumentException("wishlist is null");
        }
        return gameRepository.findGameByWishlist(wishlist);
    }


    //games with less stock
    /**
     * @param n
     * @return
     */
    public List<Game> getGamesLowerInstockThan(int n){
        if(n < 0){
            throw new IllegalArgumentException("search in stock quantity cannot be lower than 0");
        }
        List<Game> allGames = this.getAllGames();
        ArrayList<Game> games = new ArrayList<>();
        for(Game game: allGames){
            if(this.getStockQuantity(game.getId()) <= n){
                games.add(game);
            }
        }
        return games;
    }
    
    //all available games
    /**
     * @return
     */
    public List<Game> getAvailableGames() {
        return this.getGamesLowerInstockThan(0);
    }

    //if game is available
    /**
     * @param id
     * @return
     */
    public boolean getGameAvailabilityById(Long id){
        return (this.getStockQuantity(id) > 0);
    }

    // does game have promoition
    /**
     * @param id
     * @return
     */
    public boolean getGamePromotionStatusById(Long id) {
        Game game = this.getGameById(id);
        Promotion promotion = game.getPromotion();
        if (promotion == null){
            return false;
        }
        Date endDate = promotion.getEndDate();
        Date startDate = promotion.getStartDate();
        Date now = Date.valueOf(LocalDate.now());
        if (endDate.after(now) && startDate.before(now)){
            return true;
        }
        if (endDate.equals(now) || startDate.equals(now)){
            return true;
        }
    return false;
    }
    
    // add game to promo
    /**
     * @param id
     * @param promo
     * @return
     */
    public Game updatePromotion(Long id, Promotion promo){
        Game game = this.getGameById(id);
        game.setPromotion(promo);
        return gameRepository.save(game);
    }

    // add game to wishlist
    /**
     * @param id
     * @param wishlist
     * @return
     */
    public Game updateWishlist(Long id, Wishlist wishlist) {

        Game game = this.getGameById(id);
        game.setWishlist(wishlist);
        return gameRepository.save(game);
    }
    

    // get price with promo
    /**
     * @param id
     * @return
     */
    public int getPriceAfterPromoWithId(Long id){
        Game game = this.getGameById(id);
        int priceAfter = game.getPrice();
        if (!this.getGamePromotionStatusById(id)){
            return priceAfter;
        }
        
        int discount = game.getPromotion().getPercentage();
        priceAfter = priceAfter * (1 - discount);
        return priceAfter;
    }

    //get all game names by order
    /**
     * @param order
     * @return
     */
    public List<Game> getGameByOrder(SpecificOrder order){
        if (order == null){
            throw new IllegalArgumentException("order cannot be null");
        }
        List<Game> games = new ArrayList<>();
        List<SpecificGame> copies = specificGameRepository.findSpecificGameBySpecificOrder(order);
        for (SpecificGame copy: copies){
            Game game = copy.getGame();
            if (games.contains(game)){
                continue;
            }
            games.add(game);
        }

        return games;
    }

    /**
     * @param min
     * @param max
     * @return
     */
    public List<Game> getGamesBetweenPrices(int min, int max){
        List<Game> allGames = this.getAllGames();
        List<Game> games = new ArrayList<>();
        int price = 0;
        for (Game game: allGames){
            price = game.getPrice();
            if(price >= min || price<= max){
                games.add(game);
            }
        }
        return games;
    }

    //filter by Promotion percentage
    /**
     * @param min
     * @return
     */
    public List<Game> getGamesAbovePromotion(int min){
        List<Game> allGames = this.getAllGames();
        List<Game> games = new ArrayList<>();
        int discount = 0;
        for (Game game: allGames){
            if(!this.getGamePromotionStatusById(game.getId())){
                continue;
            }
            else{
                discount = game.getPromotion().getPercentage();
                if (discount >= min){
                    games.add(game);
                }
            }
        }
        return games;
    }

    //get stock quantity by id
    /**
     * @param id
     * @return
     */
    public int getStockQuantity(Long id){
        List<SpecificGame> copies = specificGameRepository.findSpecificGameByGame(this.getGameById(id));
        int stockQuantity = 0;
        for (SpecificGame copy: copies){
            if (!copy.hasOrder()){
                stockQuantity += 1;
            }
        }
        return stockQuantity;
    }

    /**
     * @param id
     * @return
     */
    public Game getGameBySpecificGameID(int id){
        if (id == 0){
            throw new IllegalArgumentException("specific game id cannot be 0");
        }
        SpecificGame specificGame = specificGameRepository.findSpecificGameBySerialNumber(id);
        if (specificGame == null){
            throw new IllegalArgumentException("the specific game cannot be null");
        }
        return specificGame.getGame();
    }

    
}
