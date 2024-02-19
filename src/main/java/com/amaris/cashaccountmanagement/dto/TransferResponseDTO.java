package com.amaris.cashaccountmanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponseDTO {

	private String moneyTransferId;
	private String status;
	private String direction;
	private CreditorDTO creditor;
	private CreditorDTO debtor;
	private String cro;
	private String uri;
	private String trn;
	private String description;
	private String createdDatetime;
	private String accountedDatetime;
	private String debtorValueDate;
	private String creditorValueDate;
	private AmountDTO amount;
	private boolean isUrgent;
	private boolean isInstant;
	private String feeType;
	private String feeAccountId;
	private List<FeeDTO> fees;
	private boolean hasTaxRelief;

}
