/*
 *
 *  * Copyright 2016 Spotify AB.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 */

package com.spotify.bigtable.read;

import com.google.bigtable.v2.Cell;
import com.google.bigtable.v2.Column;
import com.google.bigtable.v2.RowFilter;

import java.util.Optional;

public interface CellRead extends BigtableRead<Optional<Cell>> {

  class CellReadImpl extends AbstractBigtableRead<Optional<Column>, Optional<Cell>> implements CellRead {

    public CellReadImpl(final BigtableRead.Internal<Optional<Column>> column) {
      super(column);

      final RowFilter.Builder cellFilter = RowFilter.newBuilder().setCellsPerColumnLimitFilter(1);
      addRowFilter(cellFilter);
    }

    @Override
    protected Optional<Cell> parentDataTypeToDataType(final Optional<Column> column) {
      return column.flatMap(c -> AbstractBigtableRead.headOption(c.getCellsList()));
    }
  }
}
