import { AccountService } from './account.service';
import { Client } from '@grpc/grpc-js';

describe('Accoutn service', () => {

    let accountService: AccountService;

    beforeEach(() => {
        accountService = new AccountService();
    });

    it('should call get account by Id ', () => {
        spyOn(accountService.gRPCClient, 'findById');

        accountService.getById(1).then();

        expect(accountService.gRPCClient.findById).toHaveBeenCalledWith({accountId: 1}, expect.anything() );
    });

    it('should define a gRPC client with findById', () => {

        expect(accountService.gRPCClient).toEqual(expect.any(Client));
        expect(accountService.gRPCClient.findById).toBeDefined();
    });

});