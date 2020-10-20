export interface Account {
    
     /**
     * The accounts's id
     * @IsInt
     */
    id: number;

    /**
     * Account's total amount
     * @IsFloat
     */
    balance: number;

    /**
     * Account Type
     */
    accountType: string;
}