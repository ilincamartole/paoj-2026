# Sistem de Gestionare Licitatii (Auction System)

Această aplicație este un sistem robust de gestionare a licitațiilor dezvoltat în Java, care facilitează administrarea utilizatorilor, a produselor și a ofertelor prin intermediul unei interfețe consolă.

## 1.1 — Lista acțiunilor / interogărilor posibile în sistem

1. **Creează o licitație nouă** pentru un produs specific.
2. **Afișează toate licitațiile** disponibile.
3. **Adaugă un utilizator nou** (înregistrare ca Buyer sau Seller).
4. **Afișează toți utilizatorii** înregistrați, grupați după tip.
5. **Adaugă o ofertă** la o licitație existentă (validată prin sumă minimă).
6. **Șterge o licitație** din sistem pe baza ID-ului produsului.
7. **Caută licitații** după numele produsului.
8. **Afișează licitațiile sortate** descrescător după numărul de oferte primite.
9. **Afișează categoria preferată** a cumpărătorilor (analiză statistică).
10. **Promovează cumpărătorii activi** (cu peste 3 participări) la statutul de **PremiumBuyer**.
11. **Afișează istoricul produselor** unui vânzător, sortate alfabetic după nume.

## 1.2 — Lista tipurilor de obiecte din domeniu

1. **User** (Clasă abstractă de bază).
2. **Buyer** 
3. **PremiumBuyer** (extends Buyer)
4. **Seller** 
5. **Licitatie** 
6. **Oferta** 
7. **Produs** 
8. **Categorie** (Enum pentru clasificarea produselor).
9. **UserType** (Enum pentru rolurile utilizatorilor).
10. **LicitatieService** -gestioneaza licitatiile
11. **UserService**- gestioneaza utilizatorii

