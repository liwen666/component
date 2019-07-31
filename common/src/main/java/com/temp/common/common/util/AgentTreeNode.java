package com.temp.common.common.util;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentTreeNode  implements Serializable {

    private Long parentId;
    private Long selfId;
    protected List<AgentTreeNode> childList;
}
