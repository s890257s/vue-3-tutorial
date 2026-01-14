import { defineStore } from "pinia";
import { ref } from "vue";
import { getProducts, createProduct, updateProduct, deleteProduct } from "@/api/products";

// 定義商品 Store
// 觀念與 memberStore 相同，請參考 memberStore.js 的詳細註解
export const useProductStore = defineStore("product", () => {
  // State: 商品列表與總筆數
  const products = ref([]);
  const totalItems = ref(0);

  // Action: 取得商品列表 API
  const fetchProducts = async (page, size) => {
    const response = await getProducts(page, size);
    products.value = response.data.content;
    totalItems.value = response.data.totalElements;
  };

  // Action: 新增商品 API
  const create = async (product) => {
    await createProduct(product);
  };

  // Action: 更新商品 API
  const update = async (id, product) => {
    await updateProduct(id, product);
  };

  // Action: 刪除商品 API
  const remove = async (id) => {
    await deleteProduct(id);
  };

  return {
    products,
    totalItems,
    fetchProducts,
    create,
    update,
    remove,
  };
});
