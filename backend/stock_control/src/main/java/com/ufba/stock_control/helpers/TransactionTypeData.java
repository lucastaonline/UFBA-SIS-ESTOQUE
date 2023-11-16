package com.ufba.stock_control.helpers;
/*
 * compra - entrada
venda - saida
descarte - saida
extorno cliente - entrada
extorno distribuidor - saida
 * 
 */
import com.ufba.stock_control.entities.TransactionDescription;
import com.ufba.stock_control.entities.TransactionDirection;
import com.ufba.stock_control.entities.TransactionType;
import com.ufba.stock_control.repositories.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionTypeData {
  
  @Autowired
  private TransactionTypeRepository transactionTypeRepository;
  
  
  @EventListener
  public void appReady(ApplicationReadyEvent event) {
    TransactionType transactionType = TransactionType.builder()
      .direction(TransactionDirection.INLET)
      .description(TransactionDescription.BUY)
      .build();
      transactionTypeRepository.save(transactionType);

    TransactionType transactionType2 = TransactionType.builder()
      .direction(TransactionDirection.OUTLET)
      .description(TransactionDescription.SELL)
      .build();
      transactionTypeRepository.save(transactionType2);
    
    TransactionType transactionType3 = TransactionType.builder()
      .direction(TransactionDirection.INLET)
      .description(TransactionDescription.CUSTOMER_CHARGEBACK)
      .build();
      transactionTypeRepository.save(transactionType3);
    
    TransactionType transactionType4 = TransactionType.builder()
      .direction(TransactionDirection.OUTLET)
      .description(TransactionDescription.DISTRIBUTOR_CHARGEBACK)
      .build();
      transactionTypeRepository.save(transactionType4);
    
    TransactionType transactionType5 = TransactionType.builder()
      .direction(TransactionDirection.OUTLET)
      .description(TransactionDescription.DISCARD)
      .build();
      transactionTypeRepository.save(transactionType5);

      TransactionType transactionType6 = TransactionType.builder()
      .direction(TransactionDirection.INLET)
      .description(TransactionDescription.CREDIT_ADJUST)
      .build();
      transactionTypeRepository.save(transactionType6);

      TransactionType transactionType7 = TransactionType.builder()
      .direction(TransactionDirection.OUTLET)
      .description(TransactionDescription.DEBIT_ADJUST)
      .build();
      transactionTypeRepository.save(transactionType7);
  }
  
  
}
