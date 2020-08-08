// const searchForm = document.getElementById('search-form')
// const searchTerm = document.getElementById('search-term')
// const cards = document.getElementById('cards')
// const product1Button = document.getElementById('btn1')
// const product2Button = document.getElementById('btn2')
// const productsEls = document.querySelectorAll('.card-title')

// let cart = []
// let priceTotal = 0

// product1Button.addEventListener('click', handleBtn1Click)
// product2Button.addEventListener('click', handleBtn2Click)
// searchForm.addEventListener('submit', (e) => {
//     e.preventDefault()
//     const currentInput = searchTerm.value.toLowerCase()
//     for (let product of productsEls) {
//         if (!product.innerHTML.toLowerCase().includes(currentInput)) {
//             product.parentElement.parentElement.style.display = 'none'
//         }
//     }
// })
// searchTerm.addEventListener('click', () => {
//     searchTerm.value = ''
//     const productEls = document.querySelectorAll('.card-title')
//     for (let product of productEls) {
//         product.parentElement.parentElement.style.display = 'block'
//     }
// })

// function handleBtn1Click(e) {
//     let product = {
//         id: Date.now(),
//         name: document.getElementById('name1').textContent,
//         description: document.getElementById('desc1').textContent,
//         price: document.getElementById('price1').textContent,
//         // image: document.getElementById('img1').src,
//     }
//     cart.push(product)

//     console.warn('added', { cart })
//     getTotalPrice(product)
//     writeCartItem(product)

// }

// function handleBtn2Click(e) {
//     let product = {
//         id: Date.now(),
//         name: document.getElementById('name2').textContent,
//         description: document.getElementById('desc2').textContent,
//         price: document.getElementById('price2').textContent,
//         // image: document.getElementById('img2').src,
//     }

//     cart.push(product)
//     console.warn('added', { cart })
//     getTotalPrice(product)
//     writeCartItem(product)
// }

// function writeCartItem(product) {
//     let cartList = document.getElementById('cart-list')
//     let li = document.createElement('li')
//     li.innerHTML = `<h6>Product: ${product.name}</h6><p>Description: ${product.description}</p><img src=${product.image} class="cart-image" />`
//     cartList.appendChild(li)
//     document.getElementById('item-num').textContent = `Number of Items: ${cart.length.toString()}`

//     let remove = document.createElement('button')
//     remove.innerHTML = 'Remove'
//     remove.addEventListener('click', () => {
//         cartList.removeChild(li)
//         removePriceFromTotal(product)
//         cart.pop(product)

//         document.getElementById('item-num').textContent = `Number of Items: ${cart.length.toString()}`

//     })
//     li.append(remove)

// }

// function getTotalPrice(product) {


//     let numPrice = Number(product.price.slice(1)) * 100
//     console.log(numPrice)
//     priceTotal = priceTotal * 100
//     priceTotal += numPrice
//     priceTotal = priceTotal / 100

//     console.log(priceTotal)
//     document.getElementById(
//         'price-total'
//     ).textContent = `Total Price: ${'$'.concat(priceTotal.toFixed(2).toString())}`
// }


// function removePriceFromTotal(product) {
//     if (cart.length === 0) {
//         priceTotal = 0
//     } else {
//         let numPrice = Number(product.price.slice(1)) * 100
//         priceTotal = priceTotal * 100
//         priceTotal -= numPrice
//         priceTotal = priceTotal / 100
//         document.getElementById(
//             'price-total'
//         ).textContent = `Total Price: ${'$'.concat(priceTotal.toFixed(2).toString())}`
//     }
// }