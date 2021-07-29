package com.bat.velo.repository;

import com.bat.velo.entity.Tokenisation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;

@Repository
public interface TokenizationRepository extends CrudRepository<Tokenisation, Long> {

    @Query(value = "SELECT * FROM vlo_tokenisation fv where fv.user_id = :userId and fv.token_number = :token and fv.token_type = :tokenType ",
            nativeQuery=true
    )
    Tokenisation findToken(long userId, String token, int tokenType);
            
    
    @Query(value = "SELECT * FROM vlo_tokenisation fv where fv.Token_Number = :tokenNumber and fv.token_type = 2",
            nativeQuery=true
    )
    Tokenisation findByTokenisationByTokenNumber(String tokenNumber);

    @Query(value = "SELECT ID FROM vlo_tokenisation WHERE Expired_Time < :comparingTime AND token_type=1 AND Phone_Number=:phoneNumber AND Token_Number=:tokenNumber And User_Id=:userId",
            nativeQuery = true)
    Integer findValidateToken(Timestamp comparingTime, Integer userId, String tokenNumber, String phoneNumber);

    @Query(value = "SELECT * FROM vlo_tokenisation WHERE Token_Number=:tokenNumber AND token_type=3",
            nativeQuery = true)
    Integer findOneUserId(String tokenNumber);
    
    @Query(value = "SELECT * from vlo_tokenisation where Token_Number=:tokenNumber and token_type=:tokenTipe",nativeQuery = true)
    Tokenisation findByTokenNumberAndTokenTypes(String tokenNumber, Integer tokenTipe);

    @Query(value = "SELECT * from vlo_tokenisation where user_id=:userId and token_type=:tokenTipe",nativeQuery = true)
    Tokenisation findByUserIdAndTokenType(long userId, Integer tokenTipe);

    Tokenisation findByTokenTypeAndTokenNumberAndPhoneNumber(Integer tokenType, String tokenNumber, String phoneNumber);

}