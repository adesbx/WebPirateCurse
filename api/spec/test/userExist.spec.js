import { verifyUserExist } from "../../services/resources.js";

describe('verifyUserExist', () => {
    it('vérifie si l"utilisateur existe bien dans data.json', async () => {
        const user1 = "toto"; 
        const res= await verifyUserExist(user1);
        expect(res).toBe(true);
    });
});

describe('verifyUserExist', () => {
    it('vérifie si l"utilisateur n"existe pas dans data.json', async () => {
        const user2 = "caca";
        const res2= await verifyUserExist(user2);
        expect(res2).toBe(false);
    });
});